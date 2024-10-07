import requests
import datetime

api_key = 'JIML76KK9ZBXIRG5'
api_key2 = "4Z0OQMUSDS4UJ7GU"

current_day = datetime.datetime.today()
yesterday = current_day - datetime.timedelta(days = 1)

def testFun(keyword):

    try:
        url = f'https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol={keyword}&apikey={api_key}'
        r = requests.get(url)
        data = r.json()
        if data == "":
            # recent_date = next(iter(data['Time Series (Daily)']))
            # close_price = data['Time Series (Daily)'][recent_date]['4. close']
            return "No data found"
        else:
            return data["Time Series (Daily)"][yesterday.strftime("%Y-%m-%d")]["4. close"]
    except:
        return f"Cannot retrieve data. Check keyword or your internet connection {keyword}"

def get_yesterday():
    return yesterday.strftime("%Y-%m-%d")

# def usd_to_php(currency_value):
#     if(isinstance(currency_value, string)):
#         php = ""
#     elif(isinstance(currency_value, float))
#         php = c.convert(currency_value, "USD", "PHP")
#     return php