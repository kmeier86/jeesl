package org.jeesl.web.rest.system.io;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.jeesl.api.facade.io.JeeslIoDbFacade;
import org.jeesl.api.rest.system.io.db.JeeslIoDbRestExport;
import org.jeesl.api.rest.system.io.db.JeeslIoDbRestImport;
import org.jeesl.api.rest.system.io.db.JeeslIoDbRestInterface;
import org.jeesl.controller.monitor.DataUpdateTracker;
import org.jeesl.factory.builder.io.IoDbFactoryBuilder;
import org.jeesl.factory.ejb.system.io.db.EjbDbDumpFileFactory;
import org.jeesl.factory.ejb.system.io.db.EjbIoDumpFactory;
import org.jeesl.factory.ejb.system.status.EjbStatusFactory;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDump;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpFile;
import org.jeesl.interfaces.model.system.io.db.JeeslDbDumpStatus;
import org.jeesl.interfaces.model.system.io.db.JeeslDbHost;
import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.jeesl.model.xml.jeesl.Container;
import org.jeesl.web.rest.AbstractJeeslRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.sync.DataUpdate;
import net.sf.exlp.xml.io.Dir;
import net.sf.exlp.xml.io.File;

public class IoDbRestService<L extends UtilsLang,D extends UtilsDescription,
							SYSTEM extends JeeslIoSsiSystem,
							DUMP extends JeeslDbDump<SYSTEM,FILE>,
							FILE extends JeeslDbDumpFile<DUMP,HOST,STATUS>,
							HOST extends JeeslDbHost<HOST,L,D,?>,
							STATUS extends JeeslDbDumpStatus<STATUS,L,D,?>>
					extends AbstractJeeslRestService<L,D>
					implements JeeslIoDbRestInterface,JeeslIoDbRestExport,JeeslIoDbRestImport
{
	final static Logger logger = LoggerFactory.getLogger(IoDbRestService.class);
	
	private final JeeslIoDbFacade<L,D,SYSTEM,DUMP,FILE,HOST,STATUS> fDb;
	private final IoDbFactoryBuilder<L,D,SYSTEM,DUMP,FILE,HOST,STATUS> fbDb;
	
	private EjbIoDumpFactory<SYSTEM,DUMP> efDump;
	private EjbDbDumpFileFactory<DUMP,FILE,HOST,STATUS> efDumpFile;
	private EjbStatusFactory<HOST,L,D> efHost; 
	
	public IoDbRestService(JeeslIoDbFacade<L,D,SYSTEM,DUMP,FILE,HOST,STATUS> fDb, IoDbFactoryBuilder<L,D,SYSTEM,DUMP,FILE,HOST,STATUS> fbDb)
	{
		super(fDb,fbDb.getClassL(),fbDb.getClassD());
		this.fDb = fDb;
		this.fbDb=fbDb;
		
		efHost = EjbStatusFactory.createFactory(fbDb.getClassHost(),fbDb.getClassL(),fbDb.getClassD());
		
		efDump = fbDb.dump();
		efDumpFile = fbDb.file();
	}
	
//	@Override public Container exportSystemDbActivityState() {return xfContainer.build(fDb.allOrderedPosition(cCategory));}
	@Override public Container exportSystemIoDbDumpStatus() {return xfContainer.build(fDb.allOrderedPosition(fbDb.getClassStatus()));}
	
	@Override public DataUpdate uploadDumps(Dir directory)
	{
		DataUpdateTracker dut = new DataUpdateTracker();
		
		STATUS eStatusStored;
		STATUS eStatusDeleted;
		
		try
		{
			eStatusStored = fDb.fByCode(fbDb.getClassStatus(),JeeslDbDumpFile.Status.stored);
			eStatusDeleted = fDb.fByCode(fbDb.getClassStatus(),JeeslDbDumpFile.Status.deleted);
		}
		catch (UtilsNotFoundException e) {dut.fail(e, true);return dut.toDataUpdate();}
		
		HOST eHost;
		try{eHost = fDb.fByCode(fbDb.getClassHost(), directory.getCode());}
		catch (UtilsNotFoundException e)
		{
			try{eHost = fDb.persist(efHost.create(directory.getCode()));}
			catch (UtilsConstraintViolationException e1) {dut.fail(e1, true);return dut.toDataUpdate();}
		}
		
		Set<FILE> setExisting = new HashSet<FILE>(fDb.fDumpFiles(eHost));
		
		for(File xFile : directory.getFile())
		{
			DUMP eDump;
			try{eDump = fDb.fByName(fbDb.getClassDump(), xFile.getName());}
			catch (UtilsNotFoundException e)
			{
				try{eDump = fDb.persist(efDump.build(xFile));}
				catch (UtilsConstraintViolationException e1) {dut.fail(e1, true);return dut.toDataUpdate();}
			}
			FILE eFile;
			try {eFile = fDb.fDumpFile(eDump,eHost);}
			catch (UtilsNotFoundException e)
			{
				try {eFile = fDb.persist(efDumpFile.build(eDump,eHost,eStatusStored));}
				catch (UtilsConstraintViolationException e1) {dut.fail(e1, true);return dut.toDataUpdate();}
			}
			
			try
			{
				if(setExisting.contains(eFile)){setExisting.remove(eFile);}
				eFile.setStatus(eStatusStored);
				eFile = fDb.update(eFile);
			}
			catch (UtilsConstraintViolationException e) {dut.fail(e,true);return dut.toDataUpdate();}
			catch (UtilsLockingException e) {dut.fail(e,true);return dut.toDataUpdate();}
		}
		
		for(FILE f : new ArrayList<FILE>(setExisting))
		{
			
			try
			{
				f.setStatus(eStatusDeleted);
				f = fDb.update(f);
			}
			catch (UtilsConstraintViolationException e) {dut.fail(e,true);}
			catch (UtilsLockingException e) {dut.fail(e,true);}
		}
		
		return dut.toDataUpdate();
	}
	
	@Override public DataUpdate importSystemDbActivityState(Aht states){logger.warn("NYI importSystemDbActivityState");return new DataUpdate();}
	@Override public DataUpdate importSystemIoDbDumpStatus(Container container){return importStatus(fbDb.getClassStatus(),container,null);}
}