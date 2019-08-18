import sys

sys.path.append("/opt")
import requests
import json

#from aws_xray_sdk.core import xray_recorder
#from aws_xray_sdk.core import patch_all
#patch_all()dep

def process_yyp(event, context):
    print('running lambda: process_yyp: '.format(event))
    try:
        pass
    except requests.RequestException as e:
        print(e)
        raise e

    ret = {"raw_id": int(pk)}
    return {
        "statusCode": 200,
        "body": json.dumps(ret),
    }


if __name__ == "__main__":
    process_yyp("", {})
