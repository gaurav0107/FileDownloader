package io.gauravdubey.FileDownloader;


import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<DownloadFile, Long> {

    DownloadFile findByFileName(String name);

}