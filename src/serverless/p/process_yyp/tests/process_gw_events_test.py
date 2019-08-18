import unittest
from process_gw_events import lambda_handler
import si_tests

class TestProcessEvents(unittest.TestCase):

    def test_process_events_default(self):
        event = si_tests.read_test_json('default.json')
        context = {}
        res = lambda_handler.process_gw_events(event, context)
        self.assertIsNotNone(res)

    def test_process_events_1(self):
        event = si_tests.read_test_json('event_1.json')
        context = {}
        res = lambda_handler.process_gw_events(event, context)
        self.assertIsNotNone(res)

    def test_process_events_2(self):
        event = si_tests.read_test_json('event_2.json')
        context = {}
        res = lambda_handler.process_gw_events(event, context)
        self.assertIsNotNone(res)

    def test_process_heartbeat(self):
        event = si_tests.read_test_json('heartbeat.json')
        context = {}
        res = lambda_handler.process_gw_events(event, context)
        self.assertIsNotNone(res)


    def test_process_events_3(self):
        event = si_tests.read_test_json('event_3.json')
        context = {}
        res = lambda_handler.process_gw_events(event, context)
        self.assertIsNotNone(res)

    def test_process_events_flood_1(self):
        event = si_tests.read_test_json('event_flood_1.json')
        context = {}
        res = lambda_handler.process_gw_events(event, context)
        self.assertIsNotNone(res)



if __name__ == "__main__":
    unittest.main()
