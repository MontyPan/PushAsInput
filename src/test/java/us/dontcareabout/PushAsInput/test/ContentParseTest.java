package us.dontcareabout.PushAsInput.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import us.dontcareabout.PushAsInput.shared.ContentParse;
import us.dontcareabout.PushAsInput.shared.Singapore;

public class ContentParseTest {

	@Test
	public void unformat() {
		Assert.assertNull(ContentParse.singapore("完全沒空格"));
		Assert.assertNull(ContentParse.singapore("1 2 "));
		Assert.assertNull(ContentParse.singapore("1 2 3 4 5"));
		Assert.assertNull(ContentParse.singapore("2張 全省 郵寄或面交 2018/09/18"));
		Assert.assertNull(ContentParse.singapore("2 全省 郵寄或面交 2018/09/181"));
		Assert.assertNull(ContentParse.singapore("2 全省 郵寄或面交 2018-09-18"));
	}

	@Test
	public void token3() {
		Singapore result = ContentParse.singapore("2 全省 郵寄或面交");
		Assert.assertEquals(2, result.getAmount());
		Assert.assertEquals("全省", result.getLocation());
		Assert.assertEquals("郵寄或面交", result.getStyle());
	}

	@Test
	public void token4() {
		Singapore result = ContentParse.singapore("2 全省 郵寄或面交 2018/09/1");
		Assert.assertEquals(2, result.getAmount());
		Assert.assertEquals("全省", result.getLocation());
		Assert.assertEquals("郵寄或面交", result.getStyle());
		Assert.assertEquals(new Date(118, 8, 1), result.getDeadline());
	}
}
