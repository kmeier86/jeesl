package org.jeesl.web.mbean.prototype.module.asset;

import java.io.Serializable;
import java.util.List;

import org.jeesl.api.bean.JeeslTranslationBean;
import org.jeesl.api.bean.callback.JeeslFileRepositoryCallback;
import org.jeesl.api.bean.msg.JeeslFacesMessageBean;
import org.jeesl.api.facade.module.JeeslAssetFacade;
import org.jeesl.factory.builder.module.AssetFactoryBuilder;
import org.jeesl.interfaces.bean.op.OpEntityBean;
import org.jeesl.interfaces.bean.sb.SbSingleBean;
import org.jeesl.interfaces.bean.sb.SbToggleBean;
import org.jeesl.interfaces.model.module.asset.JeeslAsset;
import org.jeesl.interfaces.model.module.asset.JeeslAssetManufacturer;
import org.jeesl.interfaces.model.module.asset.JeeslAssetRealm;
import org.jeesl.interfaces.model.module.asset.JeeslAssetStatus;
import org.jeesl.interfaces.model.system.locale.JeeslLocale;
import org.jeesl.web.mbean.prototype.admin.AbstractAdminBean;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public abstract class AbstractAssetBean <L extends UtilsLang, D extends UtilsDescription, LOC extends JeeslLocale<L,D,LOC,?>,
										REALM extends JeeslAssetRealm<L,D,REALM,?>, RREF extends EjbWithId,
										ASSET extends JeeslAsset<REALM,ASSET,AS>,
										MANU extends JeeslAssetManufacturer,
										AS extends JeeslAssetStatus<L,D,AS,?>>
					extends AbstractAdminBean<L,D>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAssetBean.class);
	
	protected JeeslAssetFacade<L,D,REALM,ASSET,MANU,AS> fAsset;
	
	private final AssetFactoryBuilder<L,D,REALM,ASSET,MANU,AS> fbAsset;
	
	private TreeNode tree; public TreeNode getTree() {return tree;}
    private TreeNode node; public TreeNode getNode() {return node;} public void setNode(TreeNode node) {this.node = node;}

    private REALM realm;
    private RREF realmReference;
    private ASSET asset; public ASSET getAsset() {return asset;} public void setAsset(ASSET asset) {this.asset = asset;}

	public AbstractAssetBean(AssetFactoryBuilder<L,D,REALM,ASSET,MANU,AS> fbAsset)
	{
		super(fbAsset.getClassL(),fbAsset.getClassD());
		this.fbAsset=fbAsset;
	}
	
	protected <E extends Enum<E>> void postConstructAsset(JeeslTranslationBean<L,D,LOC> bTranslation, JeeslFacesMessageBean bMessage,
									JeeslAssetFacade<L,D,REALM,ASSET,MANU,AS> fAsset,
									E eRealm, RREF realmReference
									)
	{
		super.initJeeslAdmin(bTranslation,bMessage);
		this.fAsset=fAsset;
		
		realm = fAsset.fByEnum(fbAsset.getClassRealm(),eRealm);
		this.realmReference=realmReference;
		
		reloadTree();
	}
	
	private void reloadTree()
	{
		ASSET root = null;//fAsset.load(cms.getRoot(),true);
		
		tree = new DefaultTreeNode(root, null);
//		buildTree(tree,root.getSections());
	}
	
	private void buildTree(TreeNode parent, List<ASSET> childs)
	{
		for(ASSET a : childs)
		{
			TreeNode n = new DefaultTreeNode(a,parent);
//			if(!s.getSections().isEmpty()) {buildTree(n,s.getSections());}
		}
	}
	
	public void onNodeExpand(NodeExpandEvent event) {if(debugOnInfo) {logger.info("Expanded "+event.getTreeNode().toString());}}
    public void onNodeCollapse(NodeCollapseEvent event) {if(debugOnInfo) {logger.info("Collapsed "+event.getTreeNode().toString());}}
	
	@SuppressWarnings("unchecked")
	public void onDragDrop(TreeDragDropEvent event) throws UtilsConstraintViolationException, UtilsLockingException
	{
        TreeNode dragNode = event.getDragNode();
        TreeNode dropNode = event.getDropNode();
        int dropIndex = event.getDropIndex();
        logger.info("Dragged " + dragNode.getData() + "Dropped on " + dropNode.getData() + " at " + dropIndex);
        
        logger.info("Childs of "+dropNode.getData());
        ASSET parent = (ASSET)dropNode.getData();
        int index=1;
        for(TreeNode n : dropNode.getChildren())
        {
    		ASSET child =(ASSET)n.getData();
//    		S db = fCms.load(child,false);
//    		efS.update(db,child);
//    		child.setSection(parent);
//    		child.setPosition(index);
//    		fAsset.save(child);
    		index++;
        }  
    }

    @SuppressWarnings("unchecked")
	public void onSectionSelect(NodeSelectEvent event)
    {
		logger.info("Selected "+event.getTreeNode().toString());
		asset = (ASSET)event.getTreeNode().getData();
		
    }

}