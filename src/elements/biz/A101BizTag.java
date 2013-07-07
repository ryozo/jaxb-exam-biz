package elements.biz;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import elements.fw.BizTag;

/**
 * A101業務を表すタグ情報
 * @author W.Ryozo
 * @version 1.0
 */
@XmlRootElement(name="a101tag")
public class A101BizTag extends BizTag {

	/** SerialVersionUID */
	public static final long serialVersionUID = 1L;

	/** A101情報 */
	public String a101Str;

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
	}

}
