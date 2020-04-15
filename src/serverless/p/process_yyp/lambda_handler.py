import sys
sys.path.append("/opt")

import requests
import json
import pymysql

#from aws_xray_sdk.core import xray_recorder
#from aws_xray_sdk.core import patch_all
#patch_all()dep

def process_lambda(event, context):
    print('running lambda: process_yyp: '.format(event))
    result = []
    try:
        print('connecting to RDS')
        host = 'database-1-instance-1.chnr0canmwap.us-west-2.rds.amazonaws.com'
        xu = 'admin'
        xp = 'seabreeze'
        db_name = 'yyp'
        conn = pymysql.connect(host, user=xu, password=xp, db=db_name, connect_timeout=5)
        with conn.cursor() as cur:
            cur.execute("select * from CUSTOM_CLASS")
            for row in cur:
                result.append(row)
            #print(result)

    except requests.RequestException as e:
        print(e)
        raise e

    pk = 0
    ret = {"raw_id": int(pk)}
    return {
        "body": json.dumps(result),
        "headers": {
            "Access-Control-Allow-Origin": "*"
        }
    }

if __name__ == "__main__":
    process_lambda("", {})
