import datetime

current_day = datetime.datetime.today()
yesterday = current_day - datetime.timedelta(days = 1)
day_before_yesterday = current_day - datetime.timedelta(days = 3)


def get_yesterday():
    return yesterday.strftime("%Y-%m-%d")