package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

import elements.biz.A101BizTag;
import elements.fw.BizTag;
import elements.fw.CommonTag;
import elements.fw.RootTag;

/**
 * 複数の要素を組み合わせてMarshal
 * @author W.Ryozo
 */
public class ComprexMarshal {
	/** 出力先クラス */
	private static final File OUTFILE= new File("out/complex.xml");
	/** 出力XMLの文字コード */
	private static final String ENCODING = "UTF-8";
	/** ルート要素を表すタグクラス */
	private static final Class<?> ROOT_TAG = RootTag.class;

	public static void main(String[] args) {

		// 作成対象のTagを作成
		CommonTag common = createCommonTag();
		BizTag biztag = createBizTag();

		// Marshallerの準備
		MarshallerWrapper marshallerWrapper = new ComprexMarshal().new MarshallerWrapper(ENCODING, CommonTag.class, A101BizTag.class);

		// タグの作成処理
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		OutputStream stream = null;
		XMLStreamWriter writer = null;

		try {
			stream = new FileOutputStream(OUTFILE);
			writer = factory.createXMLStreamWriter(stream, ENCODING);

			// XML文書の開始
			writer.writeStartDocument(ENCODING, "1.0");

			// Rootタグ名の取得 TODO 改善。ルートタグの扱い方を決める。
			XmlRootElement rootElement = (XmlRootElement) ROOT_TAG.getAnnotation(XmlRootElement.class);

			// Rootタグの出力
			writer.writeStartElement(rootElement.name());
			// TODO Rootタグ情報の活用

			// TODO ここはタグの順序を直接書くのではなく、動的に変更できるようにしたい。そのためにそれだけの情報をRootTagに持たせる。
			// Commonタグの出力
			marshallerWrapper.appendWriter(common, writer);

			// Bizタグの出力
			marshallerWrapper.appendWriter(biztag, writer);

			// ドキュメントを閉じる。
			writer.writeEndDocument();
			writer.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Exception e) {e.printStackTrace();}
			}

			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {e.printStackTrace(); }
			}
		}
	}

	/**
	 * MarshallerのWrapper
	 * @author W.Ryozo
	 * @param <T> marshall対象の型
	 */
	class MarshallerWrapper {
		private Marshaller marshaller = null;
		public MarshallerWrapper(String encoding, Class<?>... targetClasses) {
			try {
				JAXBContext context = JAXBContext.newInstance(targetClasses);
				marshaller = context.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * 第一引数のオブジェクトをXML表現とし、第二引数のWriterに追加する。
		 * @param jaxbElement 追加対象のオブジェクト
		 * @param writer 追加先のWriter
		 */
		public void appendWriter(Object jaxbElement, XMLStreamWriter writer) {
			try {
				marshaller.marshal(jaxbElement, writer);
			} catch (JAXBException jaxbe) {
				throw new RuntimeException(jaxbe);
			}
		}
	}

	/**
	 * テスト用のCommonTagを作成する。
	 * @return CommonTag
	 */
	private static CommonTag createCommonTag() {
		CommonTag common = new CommonTag();
		common.sessionKey = "commonkey";
		common.dateTime = "YYYY/MM/DD HH:MI:SS";
		return common;
	}

	/**
	 * テスト用のBizTagを作成する。
	 * @return
	 */
	private static BizTag createBizTag() {
		A101BizTag bizTag = new A101BizTag();
		bizTag.a101Str = "a101Str";
		bizTag.messageKey = "msgkey";

		return bizTag;
	}
}
