package de.novatec.tc.k8;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.JSON;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.CustomObjectsApi;
import io.kubernetes.client.openapi.models.V1NodeList;
import io.kubernetes.client.util.Config;
import org.json.JSONObject;

import java.io.IOException;

public class KubernetesClient {
    public static void main(String[] args) throws IOException, ApiException {
        ApiClient client = Config.defaultClient();
        CoreV1Api api = new CoreV1Api(client);

        CustomObjectsApi apiInstance = new CustomObjectsApi(client);
        String group = "group_example"; // String | The custom resource's group name
        String version = "version_example"; // String | The custom resource's version
        String plural = "plural_example"; // String | The custom resource's plural name. For TPRs this would be lowercase plural kind.
        Object body = new JSONObject().append("testKey", "testValue"); // Object | The JSON schema of the Resource to create.
        String pretty = "pretty_example"; // String | If 'true', then the output is pretty printed.
        String dryRun = "dryRun_example"; // String | When present, indicates that modifications should not be persisted. An invalid or unrecognized dryRun directive will result in an error response and no further processing of the request. Valid values are: - All: all dry run stages will be processed
        String fieldManager = "fieldManager_example"; // String | fieldManager is a name associated with the actor or entity that is making these changes. The value must be less than or 128 characters long, and only contain printable characters, as defined by https://golang.org/pkg/unicode/#IsPrint. This field is required for apply requests (application/apply-patch) but optional for non-apply patch types (JsonPatch, MergePatch, StrategicMergePatch).
        try {
            Object result = apiInstance.createClusterCustomObject(group, version, plural, body, pretty, dryRun, fieldManager);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CustomObjectsApi#createClusterCustomObject");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
