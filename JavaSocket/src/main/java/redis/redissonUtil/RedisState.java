package redis.redissonUtil;

import java.util.Date;

/**
 * @author xiezuojie
 */
public class RedisState {

    public static final String STATE_ON = "ON";
    public static final String STATE_OFF = "OFF";

    public int serverId;
    public String state;
    public Date time; // 状态变化时间

    public void on() {
        changeState(STATE_ON);
    }

    public void off() {
        changeState(STATE_OFF);
    }

    private void changeState(String st) {
//        serverId = GameUtils.ServerId;
        state = st;
        time = new Date();
    }


    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "RedisState{" +
                "state='" + state + '\'' +
                ", time=" + time +
                '}';
    }
}
