package org.freeswitch.esl.client.transport.event;

/**
 * Convenience container class for some commonly used ESL event header names (note there are many more!).
 * <p/>
 * These names are stored as strings (rather than an Enum) so that there is no necessity to keep up to
 * date with changes or additions to event header names.
 */
public class EslEventHeaderNames {

    /**
     * {@code "Event-Name"}
     */
    public static final String EVENT_NAME = "Event-Name";

    /**
     * {@code "Event-Date-Local"}
     */
    public static final String EVENT_DATE_LOCAL = "Event-Date-Local";

    /**
     * {@code "Event-Date-GMT"}
     */
    public static final String EVENT_DATE_GMT = "Event-Date-GMT";

    /**
     * {@code "Event-Date-Timestamp"}
     */
    public static final String EVENT_DATE_TIMESTAMP = "Event-Date-Timestamp";

    /**
     * {@code "Event-Calling-File"}
     */
    public static final String EVENT_CALLING_FILE = "Event-Calling-File";

    /**
     * {@code "Event-Calling-Function"}
     */
    public static final String EVENT_CALLING_FUNCTION = "Event-Calling-Function";

    /**
     * {@code "Event-Calling-Line-Number"}
     */
    public static final String EVENT_CALLING_LINE_NUMBER = "Event-Calling-Line-Number";

    /**
     * {@code "FreeSWITCH-Hostname"}
     */
    public static final String FREESWITCH_HOSTNAME = "FreeSWITCH-Hostname";

    /**
     * {@code "FreeSWITCH-IPv4"}
     */
    public static final String FREESWITCH_IPV4 = "FreeSWITCH-IPv4";

    /**
     * {@code "FreeSWITCH-IPv6"}
     */
    public static final String FREESWITCH_IPV6 = "FreeSWITCH-IPv6";

    /**
     * {@code "Core-UUID"}
     */
    public static final String CORE_UUID = "Core-UUID";

    /**
     * {@code "Content-Length"}
     */
    public static final String CONTENT_LENGTH = "Content-Length";

    /**
     * {@code "Job-Command"}
     */
    public static final String JOB_COMMAND = "Job-Command";

    /**
     * {@code "Job-UUID"}
     */
    public static final String JOB_UUID = "Job-UUID";

    private EslEventHeaderNames() {
        /* private class */
    }

    public enum EventName {
        //*******************************************************************************
        //   CUSTOME: Event-Name is CUSTOME, usage for models (mod_conference、mod_sofia、mod_fifo)
        //*******************************************************************************
        // Event-Subclass for conference
        CUSTOME_CONFERENCE("CUSTOME", "conference::maintenaince"),

        // Event-Subclass for fifo
        CUSTOME_FIFO("CUSTOME", "fifo::info"),

        //*******************************************************************************
        //   Channel: state of Channel
        //*******************************************************************************
        // 系统中有来话或去话时，将生成一个新的Channel
        CHANNEL_CREATE("CHANNEL", "CHANNEL_CREATE"),

        // 当SIP接收方收到对方的100或108消息
        CHANNEL_PROGRESS("CHANNEL", "CHANNEL_PROGRESS"),

        // 一个Channel被应答
        CHANNEL_ANSWER("CHANNEL", "CHANNEL_ANSWER"),

        // 呼叫状态
        CHANNEL_CALLSTATE("CHANNEL", "CHANNEL_CALLSTATE"),

        // 一个Channel与另外一个Channel bridge成功，bridge是由两个Channel参与的
        CHANNEL_BRIDGE("CHANNEL", "CHANNEL_BRIDGE"),

        // 在Channel生存期间，执行App时，产生CHANNEL_EXECUTE事件，表示一个App已开始执行
        CHANNEL_EXECUTE("CHANNEL", "CHANNEL_EXECUTE"),
        // 在App执行完毕后，会产生CHANNEL_EXECUTE_COMPLETE事件
        CHANNEL_EXECUTE_COMPLETE("CHANNEL", "CHANNEL_EXECUTE_COMPLETE"),

        // 一方挂机后
        CHANNEL_HANGUP("CHANNEL", "CHANNEL_HANGUP"),
        // 比CHANNEL_HANGUP多了 variable_duration(通话时长-Channel创建开始), variable_billsec(计费时长,从应答后开始)
        CHANNEL_HANGUP_COMPLETE("CHANNEL", "CHANNEL_HANGUP_COMPLETE"),

        // Channel已经完全释放了
        CHANNEL_DESTROY("CHANNEL", "CHANNEL_DESTROY"),

        //*******************************************************************************
        //   Channel相关事件：有Channel有关，名字不是以CHANNEL_开头
        //*******************************************************************************
        // 播放声音
        PLAYBACK_START("PLAYBACK", "PLAYBACK_START"),
        PLAYBACK_STOP("PLAYBACK", "PLAYBACK_STOP"),

        // 录音
        RECORD_START("RECORD", "RECORD_START"),
        RECORD_STOP("RECORD", "RECORD_STOP"),

        // 双音多频按键信息
        DTMF("DTMF", "DTMF"),

        //*******************************************************************************
        //   系统事件
        //*******************************************************************************
        // 系统启动、关闭
        STARTUP("SYSTEM", "STARTUP"),
        SHUTDOWN("SYSTEM", "SHUTDOWN"),

        // 模块加载、卸载
        MODULE_LOAD("MODULE", "MODULE_LOAD"),
        MODULE_UNLOAD("MODULE", "MODULE_UNLOAD"),

        // 心跳：间隔20秒
        HEARTBEAT("SYSTEM", "HEARTBEAT"),

        //*******************************************************************************
        //   其他事件
        //*******************************************************************************
        // 执行API时产生
        API("API", "API"),

        // 使用bgapi后执行API时产生
        BACKGROUND_JOB("API", "BACKGROUND_JOB"),

        // 消息事件
        MESSAGE_QUERY("MESSAGE", "MESSAGE_QUERY"),
        MESSAGE_WAITING("MESSAGE", "MESSAGE_WAITING"),

        ;

        private final String type;

        private final String literal;

        EventName(String type, String literal) {
            this.type = type;
            this.literal = literal;
        }

        public String literal() {
            return literal;
        }

        public static EventName fromLiteral(String literal) {
            for (EventName name : values()) {
                if (name.literal.equalsIgnoreCase(literal)) {
                    return name;
                }
            }

            return null;
        }
    }
}
