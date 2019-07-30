package org.jeesl.web.mbean.prototype.module.bb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslBbFacade;
import org.jeesl.controller.handler.sb.SbSingleHandler;
import org.jeesl.factory.builder.module.BbFactoryBuilder;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.model.module.bb.JeeslBbBoard;
import org.jeesl.interfaces.model.module.bb.post.JeeslBbPost;
import org.jeesl.interfaces.model.module.bb.post.JeeslBbThread;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsMarkupType;
import org.jeesl.interfaces.model.system.locale.JeeslMarkup;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithEmail;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractBbPostBean <L extends UtilsLang,D extends UtilsDescription,
									SCOPE extends UtilsStatus<SCOPE,L,D>,
									BB extends JeeslBbBoard<L,D,SCOPE,BB,PUB,USER>,
									PUB extends UtilsStatus<PUB,L,D>,
									THREAD extends JeeslBbThread<BB>,
									POST extends JeeslBbPost<THREAD,M,MT,USER>,
									M extends JeeslMarkup<MT>,
									MT extends JeeslIoCmsMarkupType<L,D,MT,?>,
									USER extends EjbWithEmail>
					extends AbstractAdminBean<L,D>
					implements Serializable,SbSingleBean
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractBbPostBean.class);
	
	protected JeeslBbFacade<L,D,SCOPE,BB,PUB,THREAD,POST,M,MT,USER> fBb;
	
	private final BbFactoryBuilder<L,D,SCOPE,BB,PUB,THREAD,POST,M,MT,USER> fbBb;
	
	protected final SbSingleHandler<SCOPE> sbhScope; public SbSingleHandler<SCOPE> getSbhScope() {return sbhScope;}
	
	private List<BB> boards; public List<BB> getBoards() {return boards;} public void setBoards(List<BB> boards) {this.boards = boards;}
	private final List<THREAD> threads; public List<THREAD> getThreads() {return threads;}
	private final List<POST> postings; public List<POST> getPostings() {return postings;}
	

	protected long refId;
	protected MT markupType;
	private BB board; public BB getBoard() {return board;} public void setBoard(BB board) {this.board = board;}
	private THREAD thread; public THREAD getThread() {return thread;} public void setThread(THREAD thread) {this.thread = thread;}
	private POST post; public POST getPost() {return post;} public void setPost(POST post) {this.post = post;}

	private TreeNode tree; public TreeNode getTree() {return tree;}
    private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}
	
	public AbstractBbPostBean(BbFactoryBuilder<L,D,SCOPE,BB,PUB,THREAD,POST,M,MT,USER> fbBb)
	{
		super(fbBb.getClassL(),fbBb.getClassD());
		this.fbBb=fbBb;
		sbhScope = new SbSingleHandler<SCOPE>(fbBb.getClassScope(),this);
		threads = new ArrayList<THREAD>();
		postings = new ArrayList<POST>();
	}

	protected void postConstructBb(JeeslTranslationBean<L,D,?> bTranslation, JeeslFacesMessageBean bMessage, JeeslBbFacade<L,D,SCOPE,BB,PUB,THREAD,POST,M,MT,USER> fBb)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fBb=fBb;
		pageConfig();
		reloadBoards();
		
		try {markupType = fBb.fByCode(fbBb.getClassMarkupType(),JeeslIoCmsMarkupType.Code.text);}
		catch (UtilsNotFoundException e) {e.printStackTrace();}
	}
	
	//This method can be overriden
	protected void pageConfig()
	{
		refId = 0;
		sbhScope.setList(fBb.allOrderedPositionVisible(fbBb.getClassScope()));
		sbhScope.setDefault();
	}
	
	@Override public void selectSbSingle(EjbWithId item) throws UtilsLockingException, UtilsConstraintViolationException
	{
		reloadBoards();
		reset(true,true);
	}
	
	private void reset(boolean rBoard, boolean rThread)
	{
		if(rBoard) {board=null;}
		if(rThread) {thread=null;}
	}
	
	private void reloadBoards()
	{
		boards = fBb.fBulletinBoards(sbhScope.getSelection(), refId);
		logger.info(AbstractLogMessage.reloaded(fbBb.getClassBoard(),boards));
		tree = new DefaultTreeNode(null, null);
		for(BB b : boards)
		{
			if(b.getParent()==null)
			{
				TreeNode n = new DefaultTreeNode(b, tree);
				buildTree(n,b);
			}
		}
	}
	
	private void buildTree(TreeNode nParent, BB bbParent)
	{
		for(BB b : boards)
		{
			if(bbParent.equals(b.getParent()))
			{
				TreeNode n = new DefaultTreeNode(b,nParent);
				buildTree(n,b);
			}
		}
	}
	
    @SuppressWarnings("unchecked")
	public void onBoardSelect(NodeSelectEvent event)
    {
		logger.info("Selected "+event.getTreeNode().toString());
		board = (BB)event.getTreeNode().getData();
		board = fBb.find(fbBb.getClassBoard(),board);
		reloadThreads();
		reset(false,true);
    }
    
	private void reloadThreads()
	{
		threads.clear();
		threads.addAll(fBb.allForParent(fbBb.getClassThread(), board));
	}
	
    public void selectThread()
    {
    	reset(false,false);
    	reloadPostings();
    }
    
    public void addThread()
    {
    	thread = fbBb.ejbThread().build(board);
    	post = fbBb.ejbPost().build("none",thread,markupType);
    }
    
    public void saveThread() throws UtilsConstraintViolationException, UtilsLockingException
    {
    	thread = fBb.save(thread);
    	post.setThread(thread);
    	post = fBb.save(post);
    	reloadThreads();
    	reloadPostings();
    }
   
    private void reloadPostings()
    {
    	postings.clear();
    	postings.addAll(fBb.allForParent(fbBb.getClassPost(), thread));
    }
	
	public void onNodeExpand(NodeExpandEvent event) {if(debugOnInfo) {logger.info("Expanded "+event.getTreeNode().toString());}}
    public void onNodeCollapse(NodeCollapseEvent event) {if(debugOnInfo) {logger.info("Collapsed "+event.getTreeNode().toString());}}
}