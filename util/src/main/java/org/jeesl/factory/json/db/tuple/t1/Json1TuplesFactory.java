package org.jeesl.factory.json.db.tuple.t1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Tuple;

import org.jeesl.factory.ejb.util.EjbIdFactory;
import org.jeesl.interfaces.facade.JeeslFacade;
import org.jeesl.model.json.db.tuple.JsonTuple;
import org.jeesl.model.json.db.tuple.t1.Json1Tuple;
import org.jeesl.model.json.db.tuple.t1.Json1Tuples;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class Json1TuplesFactory <A extends EjbWithId>
{
	private final Class<A> cA; public Class<A> getClassA() {return cA;}

	private JeeslFacade fUtils; public void setfUtils(JeeslFacade fUtils) {this.fUtils = fUtils;}

	private final Set<Long> setA;
	private final Json1TupleFactory<A> jtf;
	
	protected final Map<Long,A> mapA; public Map<Long,A> getMapA() {return mapA;}
	
	private Json1Tuples<A> tuples; public Json1Tuples<A> get1Tuples() {return tuples;} public void set1Tuples(Json1Tuples<A> tuples) {this.tuples = tuples;}

	public Json1TuplesFactory(Class<A> cA) {this(null,cA);}
	public Json1TuplesFactory(JeeslFacade fUtils, Class<A> cA)
	{
		this.cA=cA;
		this.fUtils=fUtils;
		setA = new HashSet<Long>();
		mapA = new HashMap<Long,A>();
		
		jtf = new Json1TupleFactory<A>();
	}
	
	public void init(JeeslFacade fUtils, Json1Tuples<A> json)
	{
		clear();
		this.tuples = json;
		
		for(Json1Tuple<A> t : json.getTuples())
		{
			setA.add(t.getId());
		}
		
		mapA.putAll(EjbIdFactory.toIdMap(fUtils.find(cA, setA)));
	}
	
	protected void clear()
	{
		setA.clear();
		mapA.clear();
	}
	
	public List<A> toListA()
	{
		return new ArrayList<A>(mapA.values());
	}
	
	// Deprecated?
	public List<Json1Tuple<A>> add(List<Json1Tuple<A>> list)
	{
		for(Json1Tuple<A> t : list)
		{
			if(!setA.contains(t.getId())) {setA.add(t.getId());}
		}
		return list;
	}
	
	public List<A> toTuple1List(JeeslFacade fUtils)
	{
		return fUtils.find(cA,setA);
	}
	
	public List<A> toListA(Json1Tuples<A> tuples)
	{
		Set<A> set = new HashSet<A>();
		for(Json1Tuple<A> t : tuples.getTuples())
		{
			if(!set.contains(t.getEjb())) {set.add(t.getEjb());}
		}
		return new ArrayList<A>(set);
	}
	
	public Json1Tuples<A> build(List<Tuple> tuples)
	{
		Json1Tuples<A> json = new Json1Tuples<A>();
		
		for(Tuple t : tuples)
        {
			Json1Tuple<A> j = jtf.build(t);
			setA.add(j.getId());
        	json.getTuples().add(j);
        }
		ejb1Load(json);	
		return json;
	}
	
	public Json1Tuples<A> buildSum(List<Tuple> tuples)
	{
		Json1Tuples<A> json = new Json1Tuples<A>();
		
		for(Tuple t : tuples)
        {
			Json1Tuple<A> j = jtf.buildSum(t);
			setA.add(j.getId());
        	json.getTuples().add(j);
        }
		ejb1Load(json);
		return json;
	}
	
	public Json1Tuples<A> buildCount(List<Tuple> tuples)
	{
		Json1Tuples<A> json = new Json1Tuples<A>();
		
		for(Tuple t : tuples)
        {
			Json1Tuple<A> j = jtf.buildCount(t);
			setA.add(j.getId());
        	json.getTuples().add(j);
        }
		ejb1Load(json);
		return json;
	}
	
	public Json1Tuples<A> buildCountNative(List<Object> list)
	{
		Json1Tuples<A> json = new Json1Tuples<A>();
		for(Object o : list)
		{
			Json1Tuple<A> j = jtf.buildCountNative(o);
			setA.add(j.getId());
        	json.getTuples().add(j);
		}
		ejb1Load(json);
		return json;
	}
	
	public Json1Tuples<A> buildSumNative(List<Object> list)
	{
		Json1Tuples<A> json = new Json1Tuples<A>();
		for(Object o : list)
		{
			Json1Tuple<A> j = jtf.buildCountNative(o);
			setA.add(j.getId());
        	json.getTuples().add(j);
		}
		ejb1Load(json);
		return json;
	}
	
	public Json1Tuples<A> build(List<Tuple> tuples, JsonTuple.Field... fields)
	{
		Json1Tuples<A> json = new Json1Tuples<A>();
		
		for(Tuple t : tuples)
        {
			Json1Tuple<A> j = jtf.build(t,fields);
			setA.add(j.getId());
        	json.getTuples().add(j);
        }
		ejb1Load(json);
		return json;
	}
	
	private void ejb1Load(Json1Tuples<A> json)
	{
		if(fUtils==null)
		{	// A object is created and the corresponding id is set
			for(Json1Tuple<A> t : json.getTuples())
			{
				try
				{
					t.setEjb(cA.newInstance());t.getEjb().setId(t.getId());
				}
				catch (InstantiationException | IllegalAccessException e) {e.printStackTrace();}
			}
		}
		else
		{	// Here we really load the objects from the DB
			Map<Long,A> map = EjbIdFactory.toIdMap(fUtils.find(cA,setA));
			for(Json1Tuple<A> t : json.getTuples())
			{
				t.setEjb(map.get(t.getId()));
			}
		}
	}
	
	@Deprecated
	public Map<A,Json1Tuple<A>> toMap(Json1Tuples<A> tuples)
	{
		Map<A,Json1Tuple<A>> map = new HashMap<A,Json1Tuple<A>>();
		
		for(Json1Tuple<A> t : tuples.getTuples())
		{
			if(!map.containsKey(t.getEjb())) {map.put(t.getEjb(), t);}
		}
		
		return map;
	}
}