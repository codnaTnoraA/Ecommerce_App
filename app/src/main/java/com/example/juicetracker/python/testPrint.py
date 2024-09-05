import requests


api_key = 'JIML76KK9ZBXIRG5'
api_key2 = "4Z0OQMUSDS4UJ7GU"

def testFun(keyword):

    try:
        url = f'https://www.alphavantage.co/query?function=OVERVIEW&symbol={keyword}&apikey={api_key2}'
        r = requests.get(url)
        data = r.json()
        if data == "":
            return "No data found"
        else:
            return data
    except:
        return "No Internet. Cannot retrieve data"