package cz.tul;

import cz.tul.data.Author;
import cz.tul.data.Picture;
import cz.tul.data.Tag;
import cz.tul.repositories.AuthorRepository;
import cz.tul.repositories.CommentRepository;
import cz.tul.repositories.PictureRepository;
import cz.tul.repositories.TagRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.flapdoodle.embed.process.collections.Collections;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@ActiveProfiles({"test"})
@WebAppConfiguration
public class DemoApplicationTests {

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	PictureRepository pictureRepository;

	@Autowired
	TagRepository tagRepository;


	private static final String authorName1 = "Peter";
	private static final UUID authorId1 = UUID.randomUUID();
	private static final String authorName2 = "Jane";
	private static final UUID authorId2 = UUID.randomUUID();

	private static final String pictureName1 = "Veverka";
	private static final String pictureUrl1 = "https://pixabay.com/static/uploads/photo/2016/05/18/20/44/squirrel-1401509_960_720.jpg";
	private static final String tagName1 = "zvire";
	private static final Tag tg1 = new Tag(UUID.randomUUID(), tagName1);

	private static final String pictureName2 = "Obili";
	private static final String pictureUrl2 = "https://pixabay.com/static/uploads/photo/2016/01/02/00/40/wheat-1117267_960_720.jpg";
	private static final String tagName2 = "rostilna";
	private static final Tag tg2 = new Tag(UUID.randomUUID(), tagName2);



	@Before
	public void before() {
		Author author1 = new Author(authorId1, authorName1);
		Author author2 = new Author(authorId2, authorName2);

        Picture picture1 = new Picture(UUID.randomUUID(), pictureName1, pictureUrl1, new Date());
        picture1.setAuthor(author1);
        List<Tag> lT = new ArrayList<Tag>();

        lT.add(tg1);
        picture1.setTags(lT);

        Picture picture2 = new Picture(UUID.randomUUID(), pictureName2, pictureUrl2, new Date());
        picture2.setAuthor(author2);
        List<Tag> lT2 = new ArrayList<Tag>();

        lT2.add(tg2);
        picture2.setTags(lT2);

        authorRepository.save(Collections.newArrayList(
        		author1, author2
        ));

        pictureRepository.save(Collections.newArrayList(
        		picture1, picture2
        ));

		tagRepository.save(lT);
		tagRepository.save(lT2);
	}

	@Test
	public void findByName() {
		List<Picture> result = pictureRepository.findByName(pictureName1);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals(pictureName1, result.get(0).getName());

		result = pictureRepository.findByName("Randomname");
		Assert.assertEquals(0, result.size());
	}

	@Test
	public void findByAuthor() {
		List<Picture> result = pictureRepository.findByAuthorId(authorId1);
		Assert.assertEquals(1, result.size());
		for (Picture picture : result) {
			Assert.assertEquals(authorId1, picture.getAuthor().getId());
		}

		result = pictureRepository.findByAuthorId(authorId2);
		Assert.assertEquals(1, result.size());
		for (Picture picture : result) {
			Assert.assertEquals(authorId2, picture.getAuthor().getId());
		}

		result = pictureRepository.findByAuthorId(UUID.randomUUID());
		Assert.assertEquals(0, result.size());
	}

	@Test
	public void findByTags() {
		List<Picture> result = pictureRepository.findByTags(tg1);
		Assert.assertEquals(1, result.size());
		for (Picture picture : result) {
			Assert.assertTrue(picture.getTags().contains(tg1));
		}

		Tag random = new Tag(UUID.randomUUID(), "random");
		tagRepository.save(random);

		result = pictureRepository.findByTags(random);
		Assert.assertEquals(0, result.size());


		result = pictureRepository.findByTags(tg2);
		Assert.assertEquals(1, result.size());
		for (Picture picture : result) {
			Assert.assertTrue(picture.getTags().contains(tg2));
		}
	}

	@After
	public void after() {
		authorRepository.deleteAll();
		commentRepository.deleteAll();
		pictureRepository.deleteAll();
	}
}
