package com.yyp.gdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

/**
 * Import Yoga database from Google docs
 * 
 */
public class GDataSpreadSheet {

    private SpreadsheetService service;
    SpreadsheetEntry _spreadSheetEntry;

    private FeedURLFactory factory;

    public GDataSpreadSheet() throws Exception {
        factory = FeedURLFactory.getDefault();
        service = new SpreadsheetService("gdata-sample-spreadsheetimport");
    }

    /**
     * 
     * Read spreadsheet and return Map of pose names and a List of values
     * representing the column model setup for the spreadsheet.
     * 
     * 
     */
    public GDataSpreadSheet(String username, String password, String docName) throws Exception {
        this();
        
        password = "y4MV4bg2";
        service.setUserCredentials(username, password);

        SpreadsheetQuery spreadsheetQuery = new SpreadsheetQuery(factory.getSpreadsheetsFeedUrl());
        spreadsheetQuery.setTitleQuery(docName);
        SpreadsheetFeed spreadsheetFeed = service.query(spreadsheetQuery, SpreadsheetFeed.class);
        List<SpreadsheetEntry> spreadsheets = spreadsheetFeed.getEntries();
        if (spreadsheets.isEmpty()) {
            throw new Exception("No spreadsheets with that name");
        }

        _spreadSheetEntry = spreadsheets.get(0);
    }

    public Map<String, List<GDataRow>> getPostureMap(int workSheet, List<GDataColumn> keyFields) throws Exception {
        WorksheetEntry worksheet = _spreadSheetEntry.getWorksheets().get(workSheet);
        CellFeed cellFeed = service.getFeed(worksheet.getCellFeedUrl(), CellFeed.class);
        return readPostureKeys(cellFeed, keyFields);
    }

    public Map<String, List<GDataRow>> getSetKeyWarmUp(int workSheet, List<GDataColumn> keyFields) throws Exception {
        WorksheetEntry worksheet = _spreadSheetEntry.getWorksheets().get(workSheet);
        CellFeed cellFeed = service.getFeed(worksheet.getCellFeedUrl(), CellFeed.class);
        return readSetKeys(cellFeed, keyFields);
    }

    public Map<String, List<GDataRow>> getTemplates(int workSheet, List<GDataColumn> keyFields) throws Exception {
        WorksheetEntry worksheet = _spreadSheetEntry.getWorksheets().get(workSheet);
        CellFeed cellFeed = service.getFeed(worksheet.getCellFeedUrl(), CellFeed.class);
        return readTemplates(cellFeed, keyFields);
    }

    private Map<String, List<GDataRow>> readSetKeys(CellFeed cellFeed, List<GDataColumn> keyFields) throws Exception {

        try {
            Map<String, List<GDataRow>> map = new HashMap<String, List<GDataRow>>();
            List<CellEntry> cells = cellFeed.getEntries();
            List<GDataRow> list = null;
            for (CellEntry cell : cells) {
                int col = cell.getCell().getCol();
                int row = cell.getCell().getRow();
                if (row == 1) {
                    continue;
                }

                String rowKey = Integer.toString(row);
                list = map.get(rowKey);
                if (list == null) {
                    list = new ArrayList<GDataRow>();
                    map.put(rowKey, list);
                }

                String cellText = cell.getTextContent().getContent().getPlainText();
                boolean found = false;
                for (GDataColumn part : keyFields) {
                    if (col == part.colNumber) {
                        list.add(new GDataRow(part, row, cellText));
                        found = true;
                        break;
                    }
                }
                assert (found);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Map<String, List<GDataRow>> readPostureKeys(CellFeed cellFeed, List<GDataColumn> keyFields)
            throws Exception {

        try {
            Map<String, List<GDataRow>> map = new HashMap<String, List<GDataRow>>();
            List<CellEntry> cells = cellFeed.getEntries();
            List<GDataRow> list = null;
            for (CellEntry cell : cells) {
                int col = cell.getCell().getCol();
                int row = cell.getCell().getRow();
                String cellText = cell.getTextContent().getContent().getPlainText();
                if (col == 1) {
                    String asana = cellText;
                    list = map.get(asana);
                    if (list == null) {
                        list = new ArrayList<GDataRow>();
                        map.put(asana, list);
                    }
                } else {
                    boolean found = false;
                    for (GDataColumn part : keyFields) {
                        if (col == part.colNumber) {
                            list.add(new GDataRow(part, row, cellText));
                            found = true;
                            break;
                        }
                    }
                    assert (found);
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Map<String, List<GDataRow>> readTemplates(CellFeed cellFeed, List<GDataColumn> keyFields) throws Exception {

        try {
            Map<String, List<GDataRow>> map = new HashMap<String, List<GDataRow>>();
            List<CellEntry> cells = cellFeed.getEntries();
            List<GDataRow> list = null;
            for (CellEntry cell : cells) {
                int col = cell.getCell().getCol();
                int row = cell.getCell().getRow();
                if (row == 1) {
                    continue;
                }

                String rowKey = Integer.toString(row);
                list = map.get(rowKey);
                if (list == null) {
                    list = new ArrayList<GDataRow>();
                    map.put(rowKey, list);
                }

                String cellText = cell.getTextContent().getContent().getPlainText();
                boolean found = false;
                for (GDataColumn part : keyFields) {
                    if (col == part.colNumber) {
                        list.add(new GDataRow(part, row, cellText));
                        found = true;
                        break;
                    }
                }
                assert (found);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
