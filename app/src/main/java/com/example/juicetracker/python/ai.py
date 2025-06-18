import requests
import json
import config

def test_fun(prompt):
    api_key=config.open_router_api_key

    response = requests.post(
        url="https://openrouter.ai/api/v1/chat/completions",
        headers={
            "Authorization": f"Bearer {api_key}",
            "Content-Type": "application/json",
        },
        data=json.dumps({
            "model": "deepseek/deepseek-r1-0528:free",
            "messages": [
                {
                    "role": "user",
                    "content": prompt
                }
            ],

        })
    )

    print(response.content)

