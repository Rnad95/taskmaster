export type AmplifyDependentResourcesAttributes = {
    "api": {
        "amplifyDatasource": {
            "GraphQLAPIKeyOutput": "string",
            "GraphQLAPIIdOutput": "string",
            "GraphQLAPIEndpointOutput": "string"
        }
    },
    "auth": {
        "taskmaster28db12d8": {
            "IdentityPoolId": "string",
            "IdentityPoolName": "string",
            "UserPoolId": "string",
            "UserPoolArn": "string",
            "UserPoolName": "string",
            "AppClientIDWeb": "string",
            "AppClientID": "string",
            "CreatedSNSRole": "string"
        }
    },
    "storage": {
        "s315c87b86": {
            "BucketName": "string",
            "Region": "string"
        }
    },
    "geo": {
        "mapResourceName": {
            "Name": "string",
            "Style": "string",
            "Region": "string",
            "Arn": "string"
        }
    },
    "analytics": {
        "taskmaster": {
            "Region": "string",
            "Id": "string",
            "appName": "string"
        }
    },
    "predictions": {
        "translateText46114b6a": {
            "region": "string",
            "sourceLang": "string",
            "targetLang": "string"
        },
        "speechGenerator8aa26051": {
            "region": "string",
            "language": "string",
            "voice": "string"
        }
    }
}