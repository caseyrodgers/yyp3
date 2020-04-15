import unittest
from process_yyp import lambda_handler
import test_tools

class TestProcessEvents(unittest.TestCase):

    def test_process_events_default(self):
        event = test_tools.read_test_json('default.json')
        context = {}
        res = lambda_handler.process_lambda(event, context)
        self.assertIsNotNone(res)



if __name__ == "__main__":
    unittest.main()
