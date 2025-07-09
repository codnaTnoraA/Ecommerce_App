import requests
import json
import config
import re

api_key = [
    config.open_router_api_key_1,
    config.open_router_api_key_2,
    config.open_router_api_key_3,
    config.open_router_api_key_4
]
key_number = 0
def ai_price_calculate(keyword, minimum_price, maximum_price, key=key_number):

    prompt = f"""
      Return only float values. Do not explain. Round off to the nearest hundredths.
      If possible, source data from shopee.ph
      Assume you retrieved a simulated market price for the product (accuracy doesn't matter).
      Use that to optimally calculate the price of the product with these parameters:

      Product name: {keyword}

      Minimum Price: {minimum_price}

      Maximum Price: {maximum_price}

      Return a single float between the minimum and maximum price that reflects an optimal sale price, based on the simulated market price."  

      It is imperative that the process is not explained at all.
    """

    try:
        response = requests.post(
            url="https://openrouter.ai/api/v1/chat/completions",
            headers={
                "Authorization": f"Bearer {api_key[key]}",
                "Content-Type": "application/json",
            },
            data=json.dumps({
                "model": "deepseek/deepseek-chat-v3-0324:free",
                "messages": [
                    {
                        "role": "user",
                        "content": prompt
                    }
                ],
            })
        )
        item = response.content
        ai_price = re.findall("[+-]?([0-9]+([.][0-9]*))", str(item, 'utf-8'))
        return ai_price[0][0]
        # return item

    except:
        if(key < len(api_key)):
            key += 1
            return ai_price_calculate(keyword, minimum_price, maximum_price, key)
        else:
            return "0.00"