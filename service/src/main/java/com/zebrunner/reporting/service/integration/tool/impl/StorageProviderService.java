package com.zebrunner.reporting.service.integration.tool.impl;

import com.zebrunner.reporting.domain.dto.aws.FileUploadType;
import com.zebrunner.reporting.domain.dto.aws.SessionCredentials;
import com.zebrunner.reporting.service.integration.IntegrationService;
import com.zebrunner.reporting.service.integration.tool.AbstractIntegrationService;
import com.zebrunner.reporting.service.integration.tool.adapter.storageprovider.StorageProviderAdapter;
import com.zebrunner.reporting.service.integration.tool.proxy.StorageProviderProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StorageProviderService extends AbstractIntegrationService<StorageProviderAdapter> {

    private final int storageProviderTokenExpiration;

    public StorageProviderService(
            IntegrationService integrationService,
            StorageProviderProxy storageProviderProxy,
            @Value("${amazon-token-expiration}") int storageProviderTokenExpiration
    ) {
        super(integrationService, storageProviderProxy, "AMAZON");
        this.storageProviderTokenExpiration = storageProviderTokenExpiration;
    }

    public String saveFile(final FileUploadType file) {
        StorageProviderAdapter adapter = getAdapterByIntegrationId(null);
        return adapter.saveFile(file);
    }

    public void removeFile(final String linkToFile) {
        StorageProviderAdapter adapter = getAdapterByIntegrationId(null);
        adapter.removeFile(linkToFile);
    }

    public Optional<SessionCredentials> getTemporarySessionCredentials() {
        StorageProviderAdapter adapter = getAdapterByIntegrationId(null);
        return adapter.getTemporarySessionCredentials(storageProviderTokenExpiration);
    }

}
