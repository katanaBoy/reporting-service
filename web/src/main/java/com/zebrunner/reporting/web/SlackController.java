package com.zebrunner.reporting.web;

import com.zebrunner.reporting.service.integration.tool.impl.SlackService;
import com.zebrunner.reporting.web.util.swagger.ApiResponseStatuses;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("Slack API")
@CrossOrigin
@RequestMapping(path = "api/slack", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class SlackController extends AbstractController {

    private final SlackService slackService;

    public SlackController(SlackService slackService) {
        this.slackService = slackService;
    }

    @ApiResponseStatuses
    @ApiOperation(value = "Sends a notification after a test run was reviewed", nickname = "sendReviewNotification", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", paramType = "header")})
    @GetMapping("/testrun/{id}/review")
    public void sendOnReviewNotification(@PathVariable("id") long testRunId) {
        slackService.sendStatusReviewed(testRunId);
    }

    @ApiResponseStatuses
    @ApiOperation(value = "Sends a notification after a test run was finished", nickname = "sendOnFinishNotification", httpMethod = "GET")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", paramType = "header")})
    @GetMapping("/testrun/{ciRunId}/finish")
    public void sendOnFinishNotification(@PathVariable("ciRunId") String ciRunId,
                                         @RequestParam(name = "channels", required = false) String channels) {
        slackService.sendStatusOnFinish(ciRunId, channels);
    }

}
