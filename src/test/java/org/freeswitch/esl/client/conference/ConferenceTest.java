package org.freeswitch.esl.client.conference;

import org.freeswitch.esl.client.transport.message.EslMessage;
import org.junit.Test;

public class ConferenceTest extends BaseTest {

    /**
     * 邀请参会人[1000]加入会议[conf-3000]
     */
    @Test
    public void create() {
        client.sendBackgroundApiCommand("originate", "sofia/internal/1000@192.168.10.100 &conference(conf-3000@default)");
    }

    /**
     * 统计会议中的成员：conference <conf name> count
     */
    @Test
    public void count() throws Exception {
        EslMessage eslMessage = client.sendApiCommand("conference count", "");
        if (eslMessage.isReplyOk()) {
            logger.info("count success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("count fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * 列出会议中的成员：conference <conf name> list [delim <string>]|[count]
     */
    @Test
    public void list() throws Exception {
        EslMessage eslMessage = client.sendApiCommand("conference list", "");
        if (eslMessage.isReplyOk()) {
            logger.info("list success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("list fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * 锁定会议：conference <conf name> lock
     */
    @Test
    public void lock() {
        EslMessage eslMessage = client.sendApiCommand("conference conf-3000 lock", "");
        if (eslMessage.isReplyOk()) {
            logger.info("lock success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("lock fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * 解锁会议：conference <conf name> unlock
     */
    @Test
    public void unlock() {
        EslMessage eslMessage = client.sendApiCommand("conference conf-3000 unlock", "");
        if (eslMessage.isReplyOk()) {
            logger.info("lock success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("unlock fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * 会议静音：<conf name> mute <[member_id|all]|last|non_moderator> [<quiet>]
     */
    @Test
    public void mute() {
        EslMessage eslMessage = client.sendApiCommand("conference conf-3000 mute", "all");
        if (eslMessage.isReplyOk()) {
            logger.info("mute success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("mute fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * 会议取消静音：<conf name> unmute <[member_id|all]|last|non_moderator> [<quiet>]
     */
    @Test
    public void unmute() {
        EslMessage eslMessage = client.sendApiCommand("conference conf-3000 unmute", "all");
        if (eslMessage.isReplyOk()) {
            logger.info("unmute success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("unmute fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * <conf name> record <filename>
     */
    @Test
    public void record() {
        EslMessage eslMessage = client.sendApiCommand("conference conf-3000 record", "/tmp/conf_3000-1.wav");
        if (eslMessage.isReplyOk()) {
            logger.info("record success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("record fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * <conf name> norecord <[filename|all]>
     */
    @Test
    public void norecord() {
        EslMessage eslMessage = client.sendApiCommand("conference conf-3000 norecord", "all");
        if (eslMessage.isReplyOk()) {
            logger.info("norecord success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("norecord fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * conference <conf name> stop <[current|all|async|last]> [<member_id>]
     */
    @Test
    public void stop() {
        EslMessage eslMessage = client.sendApiCommand("conference conf-3000 stop", "all");
        if (eslMessage.isReplyOk()) {
            logger.info("stop success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("stop fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * conference <conf name> bgdial <endpoint_module_name>/<destination> <callerid number> <callerid name>
     */
    @Test
    public void bgdial() {
        EslMessage eslMessage = client.sendApiCommand("conference conf-3000 bgdial", "user/1001");
        if (eslMessage.isReplyOk()) {
            logger.info("bgdial success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("bgdial fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * conference <conf name> tmute <[member_id|all|last|non_moderator]> [<optional sound file>]
     */
    @Test
    public void tmute() {
        EslMessage eslMessage = client.sendApiCommand("conference conf-3000 tmute", "3");
        if (eslMessage.isReplyOk()) {
            logger.info("tmute success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("tmute fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * conference <conf name> hup <[member_id|all|last|non_moderator]> [<optional sound file>]
     */
    @Test
    public void hup() {
        EslMessage eslMessage = client.sendApiCommand("conference conf-3000 hup", "3");
        if (eslMessage.isReplyOk()) {
            logger.info("hup success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("hup fail: {}", eslMessage.getBodyLines());
        }
    }

    /**
     * conference <conf name> kick <[member_id|all|last|non_moderator]> [<optional sound file>]
     */
    @Test
    public void kick() {
        EslMessage eslMessage = client.sendApiCommand("conference conf-3000 kick", "1");
        if (eslMessage.isReplyOk()) {
            logger.info("kick success: {}", eslMessage.getBodyLines());
        } else {
            logger.error("kick fail: {}", eslMessage.getBodyLines());
        }
    }
}
