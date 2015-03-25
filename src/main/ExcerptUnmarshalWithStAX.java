package main;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import elements.fw.CommonTag;

/**
 * StAXとJAXBを利用し、部分的にアンマーシャルする。
 * @author W.Ryozo
 */
public class ExcerptUnmarshalWithStAX {

	/** アンマーシャル対象のXML */
	private static final String TARGET_PATH = "in/in.xml";

	/** アンマーシャル対象のタグクラス */
	private static final Class<?> TARGET_TAG_CLASS = CommonTag.class;

	public static void main(String[] args) {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		UnmarshallerWrapper unmashallWrapper = new UnmarshallerWrapper(CommonTag.class);

		// TODO XMLEventReaderへの変更
		XMLStreamReader xmlReader = null;

		try (BufferedReader reader = Files.newBufferedReader(Paths.get(TARGET_PATH)))
		{
			xmlReader = factory.createXMLStreamReader(reader);

			// TODO フィルタの適用。
			// StartElementだけフィルタ
//			StreamFilter filter = new StreamFilter() {
//				@Override
//				public boolean accept(XMLStreamReader reader) {
//					return reader.isStartElement();
//				}
//			};
//			reader = factory.createFilteredReader(reader, filter);

			while (xmlReader.hasNext()) {
				int eventType = xmlReader.next();

				if (eventType == XMLStreamReader.START_ELEMENT) {
					if (isTargetPoint(xmlReader)) {
						CommonTag tag = unmashallWrapper.unmarshal(xmlReader);
						System.out.println(tag);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// XmlStreamReaderはAutoCloseableをImplementsしないため手動Close
			if (xmlReader != null) {
				try {
					xmlReader.close();
				} catch (XMLStreamException xse) {
					xse.printStackTrace();
				}
			}
		}
	}

	/**
	 * 現在の読み込み位置がアンマーシャル対象のポイントであるか判定する。
	 * @param element 判定対象のタグ
	 * @return 判定結果
	 */
	public static boolean isTargetPoint(XMLStreamReader reader) throws Exception{
		if (!TARGET_TAG_CLASS.isAnnotationPresent(XmlRootElement.class)) {
			throw new RuntimeException("xmlrootelement annotation is required");
		}
		XmlRootElement elementAnnotation = TARGET_TAG_CLASS.getAnnotation(XmlRootElement.class);
		return elementAnnotation.name().equals(reader.getLocalName());
	}
}

/**
 * {@link Unmarshaller}のWrapperクラス。<br />
 * Unmarshalを担当する。
 * @author W.Ryozo
 * @param <T> アンマーシャル対象のタグクラス
 */
class UnmarshallerWrapper {
	private Unmarshaller unmarshaller;
	public UnmarshallerWrapper(Class<?>... targetClasses) {
		try {
			JAXBContext context = JAXBContext.newInstance(targetClasses);
			unmarshaller = context.createUnmarshaller();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public <T> T unmarshal(XMLStreamReader reader) throws JAXBException {
		if(reader == null) {
			throw new NullPointerException("reader is null");
		}
		// TODO 修正
		return (T) unmarshaller.unmarshal(reader);
	}
}