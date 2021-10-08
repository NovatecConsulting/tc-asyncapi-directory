package de.novatec.tc.asyncapi.reader;

import de.novatec.tc.asyncapi.AsyncApiRecord;
import de.novatec.tc.asyncapi.AsyncApiVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/asyncapi")
public class ReaderController {

    private final ReaderService readerService;

    @Autowired
    ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Set<AsyncApiRecord>> getAsyncApiSummary() {
        Optional<Set<AsyncApiRecord>> response = readerService.getAsyncApiSummary();
        return ResponseEntity.of(response);
    }

    @GetMapping(value = "/{artifactId}/latest", produces = "application/json")
    public ResponseEntity<AsyncApiRecord> getLatestVersionOfAsyncApi(@PathVariable String artifactId) {
        Optional<AsyncApiRecord> response = readerService.getLatestAsyncApiById(artifactId);
        return ResponseEntity.of(response);
    }

    @GetMapping(value = "/{artifactId}/{version}", produces = "application/json")
    public ResponseEntity<AsyncApiRecord> getSpecificVersionOfAsyncApi(@PathVariable String artifactId, @PathVariable int version) {
        Optional<AsyncApiRecord> response = readerService.getSpecificVersionOfAsyncApi(artifactId, version);
        return ResponseEntity.of(response);
    }

    @GetMapping(value = "/{artifactId}", produces = "application/json")
    public ResponseEntity<AsyncApiVersion> getHighestVersionNumberOfAsyncApi(@PathVariable String artifactId) {
        Optional<AsyncApiVersion> response = readerService.getHighestVersionNumberOfAsyncApi(artifactId);
        return ResponseEntity.of(response);
    }

}
