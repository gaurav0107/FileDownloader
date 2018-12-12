package io.gauravdubey.FileDownloader;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DownloadFileRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DownloadRepository downloadRepository;



    @Test
    public void whenFindByName_thenReturnDownloadFile() {
        // given
        DownloadFile downloadFile = new DownloadFile("testing", "http://testing.io/testing");
        entityManager.persist(downloadFile);
        entityManager.flush();

        // when
        DownloadFile found = downloadRepository.findByFileName(downloadFile.getFileName());

        // then
        System.out.println(found.getFileName());
        assertThat(found.getFileName())
                .isEqualTo(downloadFile.getFileName());
    }


}