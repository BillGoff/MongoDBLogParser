package com.snaplogic.mongodb.parser.dtos;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.snaplogic.mongodb.parser.utils.DateUtils;
import com.snaplogic.mongodb.parser.utils.LogEntryHelper;
import com.snaplogic.mongodb.parser.utils.StringUtils;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LogEntry {
	
	private String cmd;
	
	private String collection;

	private Integer docsExamined = Integer.valueOf(0);

	private Integer duration = Integer.valueOf(0);
	
	private String env;

	private String errMsg;

	@JsonProperty("_id")
	private String id;

	private Integer keysExamined = Integer.valueOf(0);
	
	@JsonProperty("logEntryDate")
	private Date logEntryDate;

	private String machine;

	@JsonProperty("msg")
	private String msg;
	
	private Integer msgId;

	private String node;

	private Integer nreturned = Integer.valueOf(0);

	private Integer numYelds = Integer.valueOf(0);

	@JsonProperty("c")
	private String operation;
	
	private String planCacheKey;

	private Integer planningTimeMicros = Integer.valueOf(0);

	private String planSummary;
	
    private String queryHash;
	
    private String readPreference;

    private String remote;
    
	private String replanReason;

	private Integer reslen = Integer.valueOf(0);

	public String getCmd() {
		return cmd;
	}

	public String getCollection() {
		return collection;
	}
	
	public Integer getDocsExamined() {
		return docsExamined;
	}
	
	public Integer getDuration() {
		return duration;
	}

	public String getEnv() {
		return env;
	}

	public String getErrMsg() {
		if(errMsg != null)
			return errMsg;
		else 
			return "";
	}

	public String getId() {
		return id;
	}
	
	public int getKeysExamined() {
		return keysExamined;
	}

	public Date getLogEntryDate() {
		return logEntryDate;
	}

	public String getMachine() {
		return machine;
	}
	
	public String getMsg() {
		return msg;
	}
    
    public Integer getMsgId() {
		return msgId;
	}

	public String getNode() {
		return node;
	}

	public Integer getNreturned() {
		return nreturned;
	}
	
	public Integer getNumYelds() {
		return numYelds;
	}

	public String getOperation() {
		return operation;
	}

	public String getPlanCacheKey() {
		return planCacheKey;
	}

	public Integer getPlanningTimeMicros() {
		return planningTimeMicros;
	}
	
	public String getPlanSummary() {
		return planSummary;
	}
	
	public String getQueryHash() {
		return queryHash;
	}

	public String getReadPreference() {
		return readPreference;
	}

	public String getRemote() {
		return remote;
	}

	public String getReplanReason() {
		return replanReason;
	}

	public Integer getReslen() {
		return reslen;
	}

	@JsonProperty("t")
	private void processLogDate(Map<String, Object> logDate) {
		try
		{
			Object temp = logDate.get("$date");
			
			if(temp != null)
			{
				this.setLogEntryDate(DateUtils.toDate((String) temp));
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public void setDocsExamined(Integer docsExamined) {
		this.docsExamined = docsExamined;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public void setEnv(String env) {
		this.env = env;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setKeysExamined(int keysExamined) {
		this.keysExamined = keysExamined;
	}

	public void setLogEntryDate(Date logEntryDate) {
		this.logEntryDate = logEntryDate;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
    
	public void setNode(String node) {
		this.node = node;
	}

	public void setNreturned(Integer nreturned) {
		this.nreturned = nreturned;
	}

	public void setNumYelds(Integer numYelds) {
		this.numYelds = numYelds;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setPlanCacheKey(String planCacheKey) {
		this.planCacheKey = planCacheKey;
	}

	public void setPlanningTimeMicros(Integer planningTimeMicros) {
		this.planningTimeMicros = planningTimeMicros;
	}

	public void setPlanSummary(String planSummary) {
		this.planSummary = planSummary;
	}

	public void setQueryHash(String queryHash) {
		this.queryHash = queryHash;
	}
	public void setReadPreference(String readPreference) {
		this.readPreference = readPreference;
	}

	public void setRemote(String remote) {
		this.remote = remote;
	}

	public void setReplanReason(String replanReason) {
		this.replanReason = replanReason;
	}
	
	public void setReslen(Integer reslen) {
		this.reslen = reslen;
	}
	
	public String toJson()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("\"queryHash\": \"" + getQueryHash()    + "\", ");
		sb.append("\"planCacheKey\": \"" + getPlanCacheKey() + "\", ");
		sb.append("\"logEntryDate\": \"" + DateUtils.dateAsPrettyString(this.getLogEntryDate()) + "\", ");
		sb.append("\"docsExamined\": \"" + getDocsExamined() + "\", ");
		sb.append("\"keysExamined\": \"" + getKeysExamined() + "\", ");
		sb.append("\"nReturned\": \""    + getNreturned()    + "\", ");
		sb.append("\"duration\": \""     + getDuration()     + "\", ");
			
		sb.append("\"query\": \""        + StringUtils.escapeSpecialChars(StringUtils.escapeDoubleQuote(getCmd())) + "\", ");
		sb.append("\"planSummary\": \""  + StringUtils.escapeDoubleQuote(getPlanSummary()) + "\", ");

		sb.append("\"errMsg\": \""       + getErrMsg() + "\", ");

		return sb.toString();
	}
	
	/**
	 * (U) Method used to printout the values of this Class.
	 *
	 * @return String nice readable string value of this class.
	 */
	@Override
	public String toString()
	{
		return (toString(""));
	}

	/**
	 * (U) Convenience method to make the string printed out indent as intended.
	 *
	 * @param tabs String value for the tabs used for indentation.
	 * @return String nice readable string value of this class.
	 */
	public String toString(String tabs)
	{
		StringBuilder sb = new StringBuilder();

		sb.append(tabs + "collection: "     + getCollection()   + "\n");
		sb.append(tabs + "keysExamined: "   + getKeysExamined() + "\n");
		sb.append(tabs + "docsExamined: "   + getDocsExamined() + "\n");
		sb.append(tabs + "queryHash: "      + getQueryHash()    + "\n");
		sb.append(tabs + "query: "          + getCmd()          + "\n");
		sb.append(tabs + "planCacheKey: "   + getPlanCacheKey() + "\n");
		sb.append(tabs + "plannedSummary: " + getPlanSummary()  + "\n");
		sb.append(tabs + "machine: "        + getMachine()      + "\n");
		sb.append(tabs + "msg: "            + getMsg()          + "\n");
		sb.append(tabs + "errMsg: "         + getErrMsg()       + "\n");
		
		return (sb.toString());
	}
	
	@JsonProperty("id")
	private void parseMsgId(Integer msgId)
	{
		this.setMsgId(msgId);
	}

	
	@JsonProperty("attr")
	private void unpackNameFromNestedObject(Map<String, Object> attr) 
	{
		try
		{
			Object temp = attr.get("command");
			if(temp != null)
			{
				this.setCmd(temp.toString());
				if(temp instanceof Map)
				{
					@SuppressWarnings("rawtypes")
					Object readPref = ((Map) temp).get("$readPreference");
					if(readPref instanceof Map)
					{
						@SuppressWarnings("rawtypes")
						Object mode = ((Map) readPref).get("mode");
						if ((mode != null) && (mode instanceof String))
							this.setReadPreference(mode.toString());
					}
				}
			}
			
			temp = attr.get("cmd");
			if(temp != null)
			{
				this.setCmd(temp.toString());
			}
			
			temp = attr.get("docsExamined");
			if ((temp != null) && (temp instanceof Integer))
				this.setDocsExamined((Integer) temp);
			
			temp = attr.get("durationMillis");
			if ((temp != null) && (temp instanceof Integer))
				this.setDuration((Integer) temp);
			
			temp = attr.get("error");
			if(temp instanceof Map)
			{
				@SuppressWarnings("rawtypes")
				Object errmsg = ((Map) temp).get("errmsg");
				if((errmsg != null) && (errmsg instanceof String))
				{
					this.setErrMsg((String) errmsg);
					this.setCollection(LogEntryHelper.parseCollectionFromError((String) errmsg));
				}
				
				@SuppressWarnings("rawtypes")
				Object errorCode = ((Map) temp).get("code");
				if((errorCode != null) && (errorCode instanceof Integer))
				{
					this.setQueryHash("E" + ((Integer) errorCode).toString());
				}
			}
			
			temp = attr.get("errMsg");
			if ((temp != null) && (temp instanceof String))
				this.setErrMsg((String) temp);
			
			temp = attr.get("keysExamined");
			if ((temp != null) && (temp instanceof Integer))
				this.setKeysExamined((Integer) temp);
			
			temp = attr.get("nreturned");
			if ((temp != null) && (temp instanceof Integer))
				this.setNreturned((Integer) temp);
			
			temp = attr.get("ns");
			if ((temp != null) && (temp instanceof String))
				this.setCollection((String) temp);
					
			temp = attr.get("numYields");
			if ((temp != null) && (temp instanceof Integer))
				this.setNumYelds((Integer) temp);
			
			temp = attr.get("planningTimeMicros");
			if ((temp != null) && (temp instanceof Integer))
				this.setPlanningTimeMicros((Integer) temp);
			
			temp = attr.get("planSummary");
			if ((temp != null) && (temp instanceof String))
				this.setPlanSummary((String) temp);
			
			temp = attr.get("remote");
			if ((temp != null) && (temp instanceof String))
				this.setRemote((String) temp);
			
			temp = attr.get("replanReason");
			if ((temp != null) && (temp instanceof String))
				this.setReplanReason((String) temp);
			
			temp = attr.get("reslen");
			if ((temp != null) && (temp instanceof Integer))
				this.setReslen((Integer) temp);
			
			temp = attr.get("queryHash");
			if ((temp != null) && (temp instanceof String))
				this.setQueryHash((String) temp);

			temp = attr.get("planCacheKey");
			if ((temp != null) && (temp instanceof String))
				this.setPlanCacheKey((String) temp);
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
