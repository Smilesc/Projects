import scrapy

class TestScraper(scrapy.Spider):
    name = "test"
    start_urls = ["https://en.wikipedia.org/wiki/Bean"]