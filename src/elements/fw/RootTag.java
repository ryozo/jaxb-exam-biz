package elements.fw;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * ルートタグを表すクラス。<br />
 * ルートタグは以下の要素を保持する。<br />
 * <ul>
 *   <li>commonタグ - 全XMLで共通的に利用する共通タグ。内部に各種管理要素を含む</li>
 *   <li>bizタグ - 個々の業務情報を保持するタグ。</li>
 * </ul>
 * @author W.Ryozo
 *
 */
@XmlRootElement(name="root")
@XmlType(propOrder={"common", "business"})
public class RootTag implements Serializable {

	/** SerialVersionUID */
	public static final long serialVersionUID = 1L;

	/** CommonTag */
	public CommonTag common;

	/**
	 * 個々の業務を表すタグ。{@link BizTag}はAbstractであり、
	 * 実際は個々の業務が利用するタグ情報を格納する。
	 */
	public BizTag business;

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
	}
}
