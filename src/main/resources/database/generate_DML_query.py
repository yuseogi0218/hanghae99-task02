from datetime import datetime, timedelta

# 현재 시간에서 하루를 뺀 시간을 초기화
current_time = datetime.now() - timedelta(days=1)

with open("data.sql", "a") as f:
    f.write("INSERT INTO ProductUserNotification(productId, userId, isActivate, createdAt, updatedAt) VALUES\n")
    for i in range(1, 3501):
        time = current_time.strftime("%Y-%m-%d %H:%M:%S")

        if i != 3500:
            f.write(f"(1, {i}, 'Y', '{time}', '{time}'),\n")
        elif i == 3500:
            f.write(f"(1, {i}, 'Y', '{time}', '{time}');\n")

        current_time += timedelta(seconds=1)

    f.write("INSERT INTO ProductUserNotification(productId, userId, isActivate, createdAt, updatedAt) VALUES\n")
    for j in range(1, 1001):
        time = current_time.strftime("%Y-%m-%d %H:%M:%S")

        if j != 1000:
            f.write(f"(2, {j}, 'Y', '{time}', '{time}'),\n")
        elif j == 1000:
            f.write(f"(2, {j}, 'Y', '{time}', '{time}');\n")
        current_time += timedelta(seconds=1)

    f.write("INSERT INTO ProductUserNotification(productId, userId, isActivate, createdAt, updatedAt) VALUES\n")
    for k in range(1, 401):
        time = current_time.strftime("%Y-%m-%d %H:%M:%S")

        if k != 400:
            f.write(f"(3, {k}, 'Y', '{time}', '{time}'),\n")
        elif k == 400:
            f.write(f"(3, {k}, 'Y', '{time}', '{time}');\n")
        current_time += timedelta(seconds=1)