import requests

api_key = 'JIML76KK9ZBXIRG5'
api_key2 = "4Z0OQMUSDS4UJ7GU"

def testFun(keyword):

    try:
        url = f'https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol={keyword}&apikey={api_key2}'
        r = requests.get(url)
        data = r.json()
        if data == "":
            # recent_date = next(iter(data['Time Series (Daily)']))
            # close_price = data['Time Series (Daily)'][recent_date]['4. close']
            return "No data found"
        else:
            return data
    except:
        return "No Internet. Cannot retrieve data"
