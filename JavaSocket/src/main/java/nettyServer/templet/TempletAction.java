package nettyServer.templet;

import nettyServer.dispatch.Response;
import nettyServer.dispatch.ResponseFactory;
import nettyServer.dispatch.annotation.Action;
import nettyServer.dispatch.annotation.Action.User;
import nettyServer.templet.entity.Templet;
import nettyServer.util.AbstractAction;
import nettyServer.util.GameExplorer;
import nettyServer.util.Head;


/**
 * @author yangxp
 *
 */
@Action
public class TempletAction extends AbstractAction{

	/**
	 * 获得自己的所有矿洞
	 * 
	 * @return
	 */
	@Action(id = Head.H9601, user = User.System)
	Response getAllMines() {
		System.out.println("66666");
		Templet templet = GameExplorer.getEntity(1, Templet.class);
		templet.update();
		return ResponseFactory.ok();
	}
}
