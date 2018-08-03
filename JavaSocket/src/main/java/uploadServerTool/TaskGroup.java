package uploadServerTool;

import com.jcraft.jsch.Session;

public abstract class TaskGroup
  extends ServerTask
{
  public TaskGroup(Server server)
  {
    super(server);
  }
  
  protected void _run(Session session)
  {
    for (ServerTask task : getGroup()) {
      task._run(session);
    }
  }
  
  protected abstract ServerTask[] getGroup();
}
