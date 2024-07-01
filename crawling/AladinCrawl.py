from bs4 import BeautifulSoup
import urllib.request
import pandas as pd

Aladin_bestseller=list()
for page in range(1,21):#전체 20개(1,21써야함)
    url=f'https://www.aladin.co.kr/shop/common/wbest.aspx?BestType=Bestseller&BranchType=1&CID=0&page={page}&cnt=1000&SortOrder=1'
    html = urllib.request.urlopen(url)
    soup=BeautifulSoup(html, 'html.parser')
    
    book_name_elems = soup.find_all('a',attrs={'class':'bo3'})#제목뽑기
    book_publisher_elems=soup.select('li a[href*="PublisherSearch"]')
    book_writer_elems=soup.select('li a[href*="AuthorSearch"]')
    book_price_elems=soup.select('span.ss_p2 span')
        
    book_names=list(map(lambda x: x.text, book_name_elems))
    book_publishers=list(map(lambda x: x.text, book_publisher_elems))
    
    beforetrans=""
    beforeMany=0
    book_writers = []
    for writer in book_writer_elems:
        # Find the parent li tag
        
        li_tag = writer.find_parent('li')
        if li_tag:
            author = writer.text
            if beforetrans!=author and beforeMany==0:
                translator = None
                all_links = li_tag.find_all('a')
                if len(all_links) > 2:
                    if len(all_links)>3:
                        for link in all_links:
                            if link != writer:
                                translator=link.text+" 외"  
                                beforeMany=len(all_links)
                                beforeMany-=2
                                break;                      
                    else:
                        for link in all_links:
                            if link != writer:
                                translator = link.text
                                beforetrans=translator
                                break
                
                if translator:
                    book_writers.append(f"{author}({translator})")
                else:
                    book_writers.append(author)
            elif beforeMany>0:
                beforeMany-=1
        else:
            book_writers.append(writer.text)
            
    book_prices=list(map(lambda x: x.text, book_price_elems))
    
    min_length=min(len(book_names), len(book_writers), len(book_publishers), len(book_prices))
    
    for i in range(min_length):
        Aladin_bestseller.append([book_names[i]]+[book_writers[i]]+
                                 [book_publishers[i]]+[book_prices[i]])
     
print(f'RESULT : \r\n{Aladin_bestseller}')
tbl=pd.DataFrame(data=Aladin_bestseller,
                 columns=('책이름','저자','출판사','가격'))

tbl.to_csv('./BestSeller.csv', sep='_', encoding='utf-8',
           mode='w', index=False)