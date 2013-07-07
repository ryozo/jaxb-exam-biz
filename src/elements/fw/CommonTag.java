package elements.fw;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 各種管理情報を保持する共通タグ
 * @author W.Ryozo
 * @version 1.0
 */
public class CommonTag implements Serializable {
	
	/** SerialVersionUID */
	public static final long serialVersionUID = 1L;
	
	/** SessionKey **/
	public String sessionKey;
	
	/** 日時情報 TODO java.util.Dateでの実装と検証 */
	public String dateTime;
	
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
	}

}
