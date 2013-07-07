package main;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import elements.biz.A101BizTag;
import elements.fw.CommonTag;
import elements.fw.RootTag;

/**
 * Marshall用テスト実行クラス
 * @author W.Ryozo
 *
 */
public class Marshal {

	private static final File OUTFILE = new File("out/out.xml");

	public static void main(String[] args) throws Exception {
		JAXBContext context = JAXBContext.newInstance(RootTag.class,A101BizTag.class);
		Marshaller marshaller = context.createMarshaller();

		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		marshaller.marshal(createTag(), OUTFILE);
		marshaller.marshal(createTag(), System.out);
	}

	private static RootTag createTag() {
		CommonTag common = new CommonTag();
		common.sessionKey = "commonsession";
		common.dateTime = "2013/07/04";

		A101BizTag aTag = new A101BizTag();
		aTag.a101Str = "A101Message";
		aTag.messageKey = "ComBizMessageKey";

		RootTag root = new RootTag();

		root.common = common;
		root.business = aTag;

		return root;
	}

}
