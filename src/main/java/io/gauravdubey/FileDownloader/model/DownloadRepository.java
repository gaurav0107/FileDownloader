package io.gauravdubey.FileDownloader.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<DownloadFile, Long> {
}