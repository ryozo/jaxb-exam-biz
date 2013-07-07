package elements.biz;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import elements.fw.BizTag;

/**
 * B101業務を表すタグ情報
 * @author W.Ryozo
 * @version 1.0
 */
public class B101BizTag extends BizTag {
	
	/** SerialVersionUID */
	public static final long serialVersionUID = 1L;
	
	/** B101情報 */
	public String b101Str;

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
	}
}
