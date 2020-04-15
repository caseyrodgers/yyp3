import os
import sys
import json

def read_test_json(name):
    wd = os.getcwd()
    f = None
    try:
        dir = os.path.dirname(__file__)
        filename = os.path.join(dir, name)
        f = open(filename, 'r')
        l = f.read()
        return json.loads(l)
    except Exception as e:
        print('error: {}'.format(sys.exc_info()))
        raise e
    finally:
        f.close()