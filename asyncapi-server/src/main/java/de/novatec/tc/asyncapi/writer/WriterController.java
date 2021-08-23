package de.novatec.tc.asyncapi.writer;

import de.novatec.tc.asyncapi.AsyncApiRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/asyncapi")
public class WriterController {

    private static Logger LOG = LoggerFactory.getLogger(WriterController.class);

    private final WriterService writerService;

    @Autowired
    WriterController(WriterService writerService) {
        this.writerService = writerService;
    }

    @PostMapping(consumes = "application/json")
    public void publishAsyncApiDefinition(@RequestBody AsyncApiRecord asyncApiRecord) {
        writerService.publishAsyncApiDefinition(asyncApiRecord.getArtifactId(), asyncApiRecord.getDefinition());
    }
}
