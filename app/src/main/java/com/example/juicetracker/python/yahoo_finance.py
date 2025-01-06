import yfinance as yf
import datetime
import json
import pandas

current_day = datetime.datetime.today()
yesterday = current_day - datetime.timedelta(days = 1)
day_before_yesterday = current_day - datetime.timedelta(days = 3)

def getStockData(keyword):
    try:
        stock_data_dataframe = yf.download(tickers = keyword, start = yesterday.strftime("%Y-%m-%d"), end = current_day.strftime("%Y-%m-%d"), ignore_tz = True)
        stock_data_json_string = stock_data_dataframe.to_json(orient = 'split')
        stock_data_json = json.loads(stock_data_json_string)
        stock_data = round(stock_data_json['data'][0][0], 2)
        return f"${stock_data}"
    # TODO fix the stock data json info display issue

    except:
        return f"Cannot retrieve data. Check keyword or your internet connection. Current keyword: {keyword}"

def get_yesterday():
    return yesterday.strftime("%Y-%m-%d")