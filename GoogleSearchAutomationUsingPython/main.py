import time
import selectOption
import excelUtils
from selenium.webdriver.common.by import By
from datetime import datetime
from selenium import webdriver

driver = webdriver.Chrome()
driver.get("https://www.google.com")
driver.maximize_window()

path = "C://Users/EmOn-Local/PycharmProjects/GoogleSearchAutomationUsingPython/dataFiles/Excel.xlsx"
today = datetime.now().strftime('%A')
# print(today)

for r in range(3, 13):
    keyword = excelUtils.readData(path, today, r, 3)
    driver.find_element(By.NAME, "q").send_keys(keyword)
    time.sleep(3)
    suggestions = driver.find_elements(By.CLASS_NAME, "wM6W7d")
    option = selectOption.shortANDlong(suggestions)
    excelUtils.writeData(path, today, r, 4, option[1])
    excelUtils.writeData(path, today, r, 5, option[0])
    driver.refresh()
    print(option)

time.sleep(3)
