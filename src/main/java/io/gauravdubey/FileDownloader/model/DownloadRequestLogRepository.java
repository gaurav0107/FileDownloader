package io.gauravdubey.FileDownloader.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DownloadRequestLogRepository extends JpaRepository<DownloadRequestLog, UUID> {
}