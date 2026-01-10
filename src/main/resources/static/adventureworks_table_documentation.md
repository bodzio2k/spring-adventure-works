# Dokumentacja Tabel - AdventureWorks Database

## Schemat Person (Osoby i Kontakty)

### BusinessEntity (Główna encja biznesowa)
- **BusinessEntityID** (int, PK) - Unikalny identyfikator
- **rowguid** (uniqueidentifier) - Guid dla synchronizacji
- **ModifiedDate** (datetime) - Data modyfikacji

### Person (Dane o osobach)
- **BusinessEntityID** (int, PK, FK) - Odniesienie do BusinessEntity
- **NameStyle** (bit) - Styl formatowania nazwy
- **Title** (nvarchar) - Tytuł (Pan/Pani)
- **FirstName** (nvarchar) - Imię
- **MiddleName** (nvarchar) - Drugie imię
- **LastName** (nvarchar) - Nazwisko
- **Suffix** (nvarchar) - Sufiks
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### Address (Adresy)
- **AddressID** (int, PK) - Unikalny identyfikator adresu
- **AddressLine1** (nvarchar) - Linia adresu 1 (wymagana)
- **AddressLine2** (nvarchar) - Linia adresu 2 (opcjonalna)
- **City** (nvarchar) - Miasto
- **StateProvinceID** (int, FK) - Odniesienie do StateProvince
- **PostalCode** (nvarchar) - Kod pocztowy
- **SpatialLocation** (geography) - Lokalizacja geograficzna (GPS)
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### StateProvince (Stany/Prowincje)
- **StateProvinceID** (int, PK) - Unikalny identyfikator
- **StateProvinceCode** (nchar(2)) - Kod stanu (np. "NY", "CA")
- **CountryRegionCode** (nvarchar(3), FK) - Kod kraju
- **IsOnlyStateProvinceFlag** (bit) - Czy to jedyne województwo
- **Name** (nvarchar) - Nazwa stanu
- **TerritoryID** (int, FK)
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### CountryRegion (Kraje/Regiony)
- **CountryRegionCode** (nvarchar(3), PK) - Kod kraju (ISO 3166-1)
- **Name** (nvarchar) - Nazwa kraju
- **ModifiedDate** (datetime)

### BusinessEntityAddress (Relacja wiele-do-wielu: osoby/firmy ↔ adresy)
- **BusinessEntityID** (int, PK, FK)
- **AddressID** (int, PK, FK)
- **AddressTypeID** (int, PK, FK)
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### AddressType (Typy adresów)
- **AddressTypeID** (int, PK) - Unikalny identyfikator
- **Name** (Name, NOT NULL) - Nazwa typu (Billing, Shipping, itp.)
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### EmailAddress (Adresy email)
- **BusinessEntityID** (int, PK, FK)
- **EmailAddressID** (int, PK) - Indeks w celu obsługi wielu emaili
- **EmailAddress** (nvarchar(50)) - Adres email
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### Phone (Numery telefonów)
- **BusinessEntityID** (int, PK, FK)
- **PhoneNumber** (nvarchar(25)) - Numer telefonu
- **PhoneNumberTypeID** (int, PK, FK)
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### PhoneNumberType (Typy numerów telefonu)
- **PhoneNumberTypeID** (int, PK)
- **Name** (nvarchar) - Nazwa typu (Cell, Home, Work)
- **ModifiedDate** (datetime)

### Password (Hasła użytkowników)
- **BusinessEntityID** (int, PK, FK)
- **PasswordHash** (varchar(128)) - Zahaszowane hasło
- **PasswordSalt** (varchar(10)) - Salt do haszowania
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

---

## Schemat HumanResources (Zasoby Ludzkie)

### Employee (Pracownicy)
- **BusinessEntityID** (int, PK, FK) - Odniesienie do Person
- **NationalIDNumber** (nvarchar(15), UK) - Numer PESEL/ID
- **LoginID** (nvarchar(256), UK) - Login do systemu (np. domain\username)
- **OrganizationNode** (hierarchyid) - Pozycja w hierarchii organizacji
- **OrganizationLevel** (smallint, computed) - Poziom organizacyjny
- **JobTitle** (nvarchar(50)) - Stanowisko
- **BirthDate** (date) - Data urodzenia
- **MaritalStatus** (nchar(1)) - Stan cywilny (M=zamężna/żonaty, S=wolna/wolny)
- **Gender** (nchar(1)) - Płeć (M=mężczyzna, F=kobieta)
- **HireDate** (date) - Data zatrudnienia
- **SalariedFlag** (bit) - Czy jest etatem czy na umowę
- **VacationHours** (smallint) - Godziny urlopu
- **SickLeaveHours** (smallint) - Godziny chorobowych
- **CurrentFlag** (bit) - Czy pracownik jest aktualnie zatrudniony
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### Department (Departamenty)
- **DepartmentID** (smallint, PK) - Unikalny identyfikator
- **Name** (Name, UK) - Nazwa departamentu
- **GroupName** (Name) - Grupa departamentów
- **ModifiedDate** (datetime)

### EmployeeDepartmentHistory (Historia przydziałów do działów)
- **BusinessEntityID** (int, PK, FK) - ID pracownika
- **DepartmentID** (smallint, PK, FK) - ID departamentu
- **ShiftID** (tinyint, PK, FK) - ID zmienności pracy
- **StartDate** (date, PK) - Data rozpoczęcia
- **EndDate** (date) - Data zakończenia (opcjonalna = ciągle)
- **ModifiedDate** (datetime)

**Opis**: Śledzi przypisanie pracowników do różnych departamentów w czasie

### Shift (Zmiany pracy)
- **ShiftID** (tinyint, PK) - Unikalny identyfikator
- **Name** (Name, UK) - Nazwa zmiany (Day, Evening, Night)
- **StartTime** (time) - Godzina rozpoczęcia
- **EndTime** (time) - Godzina zakończenia
- **ModifiedDate** (datetime)

### EmployeePayHistory (Historia wynagrodzeń)
- **BusinessEntityID** (int, PK, FK) - ID pracownika
- **RateChangeDate** (datetime, PK) - Data zmiany wynagrodzenia
- **Rate** (money) - Stawka godzinowa/dniowa
- **PayFrequency** (tinyint) - Częstotliwość wypłat (1=mies, 2=dwutygodniowe)
- **ModifiedDate** (datetime)

**Opis**: Utrzymuje historię zmian wynagrodzeń pracownika

### JobCandidate (Kandydaci na stanowiska)
- **JobCandidateID** (int, PK) - Unikalny identyfikator
- **BusinessEntityID** (int, FK) - ID osoby (opcjonalne)
- **Resume** (xml) - CV w formacie XML
- **ModifiedDate** (datetime)

---

## Schemat Sales (Sprzedaż)

### Customer (Klienci)
- **CustomerID** (int, PK) - Unikalny identyfikator klienta
- **PersonID** (int, FK) - Odniesienie do Person (opcjonalne)
- **StoreID** (int, FK) - Odniesienie do Store (opcjonalne)
- **TerritoryID** (int, FK) - Odniesienie do Territory
- **AccountNumber** (nvarchar, computed) - Numer konta (AW + CustomerID)
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

**Opis**: Klient może być osobą fizyczną, firmą, lub osobą pracującą dla firmy

### Store (Sklepy)
- **StoreID** (int, PK, FK) - Odniesienie do BusinessEntity
- **Name** (nvarchar) - Nazwa sklepu
- **SalesPersonID** (int, FK) - ID pracownika odpowiedzialnego za sklep
- **Demographics** (xml) - Dane demograficzne o sklepie
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### SalesPerson (Pracownicy sprzedaży)
- **BusinessEntityID** (int, PK, FK) - Odniesienie do Employee
- **TerritoryID** (int, FK) - Terytorialnie przypisany
- **SalesQuota** (money) - Przychód do osiągnięcia
- **Bonus** (money) - Bonus roczny
- **CommissionPct** (decimal) - Procent prowizji
- **SalesYTD** (money) - Sprzedaż od początku roku
- **SalesLastYear** (money) - Sprzedaż z poprzedniego roku
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### Territory (Terytoria sprzedażowe)
- **TerritoryID** (int, PK) - Unikalny identyfikator
- **Name** (nvarchar) - Nazwa terytoriały
- **CountryRegionCode** (nvarchar(3), FK) - Kod kraju
- **Group** (nvarchar) - Grupa terytorialnego (Europe, North America, etc.)
- **SalesYTD** (money) - Sprzedaż terytorinu w tym roku
- **SalesLastYear** (money) - Sprzedaż z poprzedniego roku
- **CostYTD** (money) - Koszty terytorialne w tym roku
- **CostLastYear** (money) - Koszty z poprzedniego roku
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### SalesOrderHeader (Nagłówki zamówień sprzedaży)
- **SalesOrderID** (int, PK) - Unikalny identyfikator zamówienia
- **OrderNumber** (nvarchar, computed) - Numer zamówienia
- **CustomerID** (int, FK) - ID klienta
- **SalesPersonID** (int, FK) - ID pracownika sprzedaży
- **TerritoryID** (int, FK) - Terytorialnie
- **OrderDate** (datetime) - Data zamówienia
- **DueDate** (datetime) - Data wymaganego dostarczenia
- **ShipDate** (datetime) - Data wysyłki
- **Status** (tinyint) - Status (1=pending, 2=approved, 3=backordered, 4=rejected, 5=shipped, 6=cancelled)
- **OnlineOrderFlag** (bit) - Czy online czy offline
- **PurchaseOrderNumber** (nvarchar) - Numer zamówienia kupna klienta
- **AccountNumber** (nvarchar) - Numer konta klienta
- **ShipToAddressID** (int, FK) - Adres wysyłki
- **BillToAddressID** (int, FK) - Adres faktury
- **ShipMethodID** (int, FK) - Metoda wysyłki
- **CreditCardApprovalCode** (nvarchar) - Kod zatwierdzenia karty
- **SubTotal** (money) - Subtotal bez podatku
- **TaxAmt** (money) - Kwota podatku
- **Freight** (money) - Koszt wysyłki
- **TotalDue** (money) - Razem do zapłaty
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### SalesOrderDetail (Szczegóły zamówień sprzedaży)
- **SalesOrderID** (int, PK, FK) - ID zamówienia
- **SalesOrderDetailID** (int, PK) - Indeks pozycji w zamówieniu
- **CarrierTrackingNumber** (nvarchar) - Numer śledzenia kuriera
- **OrderQty** (smallint) - Ilość zamówiona
- **ProductID** (int, FK) - ID produktu
- **UnitPrice** (decimal) - Cena jednostkowa
- **UnitPriceDiscount** (decimal) - Rabat na jednostkę
- **LineTotal** (money, computed) - Razem dla tej pozycji
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### ShipMethod (Metody wysyłki)
- **ShipMethodID** (int, PK)
- **Name** (nvarchar, UK) - Nazwa metody
- **ShipBase** (money) - Cena bazowa
- **ShipRate** (money) - Stawka za jednostkę
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### CreditCard (Karty kredytowe)
- **CreditCardID** (int, PK)
- **CardType** (nvarchar) - Typ karty (Visa, MasterCard, itp.)
- **CardNumber** (nvarchar(25), UK) - Numer karty
- **ExpMonth** (tinyint) - Miesiąc ważności
- **ExpYear** (smallint) - Rok ważności
- **ModifiedDate** (datetime)

### Currency (Waluty)
- **CurrencyCode** (nchar(3), PK) - Kod ISO (USD, EUR, itp.)
- **Name** (Name, UK) - Nazwa waluty
- **ModifiedDate** (datetime)

### CurrencyRate (Kursy walut)
- **CurrencyRateID** (int, PK)
- **CurrencyRateDate** (datetime) - Data kursu
- **FromCurrencyCode** (nchar(3), FK) - Z waluty
- **ToCurrencyCode** (nchar(3), FK) - Na walutę
- **AverageRate** (money) - Kurs średni
- **EndOfDayRate** (money) - Kurs na koniec dnia
- **ModifiedDate** (datetime)

### SalesReason (Przyczyny sprzedaży)
- **SalesReasonID** (int, PK)
- **Name** (nvarchar) - Nazwa przyczyny
- **ReasonType** (nvarchar) - Typ przyczyny
- **ModifiedDate** (datetime)

**Opis**: Przyczyny dlaczego klient dokonał zakupu (Marketing, Quality, Price, etc.)

---

## Schemat Production (Produkcja)

### Product (Produkty)
- **ProductID** (int, PK) - Unikalny identyfikator
- **Name** (Name, UK) - Nazwa produktu
- **ProductNumber** (nvarchar(25), UK) - Numer artykułu
- **MakeFlag** (bit) - Czy produkowany wewnętrznie
- **FinishedGoodsFlag** (bit) - Czy gotowy produkt
- **Color** (nvarchar) - Kolor
- **SafetyStockLevel** (smallint) - Poziom minimalny zapasu
- **ReorderPoint** (smallint) - Punkt zamawiania
- **StandardCost** (decimal) - Koszt standardowy
- **ListPrice** (decimal) - Cena katalogowa
- **Size** (nvarchar) - Rozmiar
- **SizeUnitMeasureCode** (nchar, FK) - Jednostka rozmiaru
- **Weight** (decimal) - Waga
- **WeightUnitMeasureCode** (nchar, FK) - Jednostka wagi
- **DaysToManufacture** (int) - Dni na produkcję
- **ProductLine** (nchar) - Linia produktu (R=Road, M=Mountain, T=Touring, S=Standard)
- **Class** (nchar) - Klasa (H=High, M=Medium, L=Low)
- **Style** (nchar) - Styl (U=Unisex, M=Mens, W=Womens)
- **ProductSubcategoryID** (int, FK)
- **ProductModelID** (int, FK)
- **SellStartDate** (datetime) - Data rozpoczęcia sprzedaży
- **SellEndDate** (datetime) - Data koniec sprzedaży
- **DiscontinuedDate** (datetime) - Data wycofania
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### ProductCategory (Kategorie produktów)
- **ProductCategoryID** (int, PK)
- **Name** (Name, UK) - Nazwa kategorii
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### ProductSubcategory (Podkategorie produktów)
- **ProductSubcategoryID** (int, PK)
- **ProductCategoryID** (int, FK) - Należy do kategorii
- **Name** (Name, UK) - Nazwa podkategorii
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### ProductModel (Modele produktów)
- **ProductModelID** (int, PK)
- **Name** (Name, UK) - Nazwa modelu
- **CatalogDescription** (xml) - Opis katalogu w XML
- **Instructions** (xml) - Instrukcje montażu w XML
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### ProductModelDescription (Opisy modeli w różnych językach)
- **ProductModelID** (int, PK, FK)
- **CultureID** (nchar(6), PK, FK) - Kod języka (en-US, de-DE, itp.)
- **Description** (nvarchar) - Opis w danym języku
- **ModifiedDate** (datetime)

### Culture (Kultury/Języki)
- **CultureID** (nchar(6), PK) - Kod ISO kultury
- **Name** (Name, UK) - Nazwa kultury
- **ModifiedDate** (datetime)

### UnitOfMeasure (Jednostki miary)
- **UnitMeasureCode** (nchar(3), PK) - Kod jednostki (PC=piece, BOX, etc.)
- **Name** (Name, UK) - Nazwa jednostki
- **ModifiedDate** (datetime)

### BillOfMaterials (Listy materiałów)
- **BillOfMaterialsID** (int, PK) - Unikalny identyfikator BOM
- **ProductAssemblyID** (int, FK) - ID produktu złożonego (opcjonalnie)
- **ComponentID** (int, FK) - ID komponentu
- **StartDate** (datetime) - Data obowiązywania od
- **EndDate** (datetime) - Data obowiązywania do
- **UnitMeasureCode** (nchar, FK) - Jednostka miary
- **BOMLevel** (smallint) - Poziom w hierarchii BOM
- **PerAssemblyQty** (decimal) - Ilość na zabudowę
- **ModifiedDate** (datetime)

**Opis**: Definiuje z jakich komponentów złożony jest produkt

### Location (Lokalizacje magazynowe)
- **LocationID** (smallint, PK)
- **Name** (Name, UK) - Nazwa lokacji
- **CostRate** (smallmoney) - Stawka kosztu
- **Availability** (decimal) - Dostępność czasu
- **ModifiedDate** (datetime)

### ProductInventory (Stan magazynu produktów)
- **ProductID** (int, PK, FK)
- **LocationID** (smallint, PK, FK) - Lokacja magazynu
- **Shelf** (nchar) - Półka
- **Bin** (tinyint) - Pojemnik
- **Quantity** (smallint) - Ilość dostępna
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### ProductPhoto (Zdjęcia produktów)
- **ProductPhotoID** (int, PK)
- **ThumbNailPhoto** (varbinary) - Miniaturka produktu
- **ThumbnailPhotoFileName** (nvarchar) - Nazwa pliku miniatury
- **LargePhoto** (varbinary) - Duże zdjęcie
- **LargePhotoFileName** (nvarchar) - Nazwa pliku dużego zdjęcia
- **ModifiedDate** (datetime)

### Illustration (Ilustracje produktów)
- **IllustrationID** (int, PK)
- **Diagram** (xml) - Diagram w formacie XML
- **ModifiedDate** (datetime)

### ProductModelIllustration (Relacja Model ↔ Ilustracja)
- **ProductModelID** (int, PK, FK)
- **IllustrationID** (int, PK, FK)
- **ModifiedDate** (datetime)

### Document (Dokumenty)
- **DocumentNode** (hierarchyid, PK) - Hierarchiczna ścieżka w systemie plików
- **DocumentLevel** (smallint, computed) - Poziom w hierarchii
- **Title** (nvarchar) - Tytuł dokumentu
- **Owner** (int, FK) - ID właściciela (pracownika)
- **FolderFlag** (bit) - Czy to folder
- **FileName** (nvarchar, UK) - Nazwa pliku
- **FileExtension** (nvarchar) - Rozszerzenie pliku
- **Revision** (nchar) - Wersja dokumentu
- **ChangeNumber** (int) - Numer zmian
- **Status** (tinyint) - Status (1=in process, 2=approved, 3=obsolete)
- **DocumentSummary** (nvarchar) - Streszczenie
- **Document** (varbinary) - Zawartość binarna dokumentu
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### WorkOrder (Rozkazy pracy/Produkcji)
- **WorkOrderID** (int, PK)
- **ProductID** (int, FK) - Produkt do wyprodukowania
- **OrderQty** (int) - Ilość do wyprodukowania
- **StockedQty** (int) - Ilość złożona do magazynu
- **ScrappedQty** (smallint) - Ilość odpadów
- **StartDate** (datetime) - Data rozpoczęcia produkcji
- **EndDate** (datetime) - Data zakończenia
- **DueDate** (datetime) - Data wymaganego dostarczenia
- **ScrapReasonID** (smallint, FK) - Przyczyna odpadów
- **rowguid** (uniqueidentifier)
- **ModifiedDate** (datetime)

### ScrapReason (Przyczyny odpadów)
- **ScrapReasonID** (smallint, PK)
- **Name** (Name, UK) - Nazwa przyczyny
- **ModifiedDate** (datetime)

### TransactionHistory (Historia transakcji/Ruchów magazynowych)
- **TransactionID** (int, PK)
- **ProductID** (int, FK)
- **ReferenceOrderID** (int) - ID dokumentu referencyjnego
- **ReferenceOrderLineID** (int) - Linia w dokumencie
- **TransactionDate** (datetime) - Data transakcji
- **TransactionType** (nchar) - Typ (W=Work Order, S=Sales Order, P=Purchase Order)
- **Quantity** (int) - Ilość
- **ActualCost** (money) - Rzeczywisty koszt
- **ModifiedDate** (datetime)

---

## Schemat Purchasing (Zakupy)

### Vendor (Dostawcy)
- **VendorID** (int, PK, FK) - Odniesienie do BusinessEntity
- **Name** (Name, UK) - Nazwa dostawcy
- **ActiveFlag** (bit) - Czy aktywny
- **CreditRating** (nvarchar) - Ocena kredytowa (1-5)
- **PreferredVendorFlag** (bit) - Preferowany dostawca
- **AccountNumber** (nvarchar, UK) - Numer konta u dostawcy
- **MinOrderQty** (money) - Minimalna ilość zamówienia
- **MaxOrderQty** (money) - Maksymalna ilość zamówienia
- **OnOrderQty** (int) - Ilość w otwartych zamówieniach
- **UnitMeasureCode** (nchar, FK)
- **ExpectedDeliveryDate** (datetime) - Spodziewana data dostawy
- **ModifiedDate** (datetime)

### ProductVendor (Relacja Produkt ↔ Dostawca)
- **ProductID** (int, PK, FK)
- **VendorID** (int, PK, FK)
- **AverageLeadTime** (int) - Średni czas dostawy w dniach
- **StandardPrice** (money) - Standardowa cena
- **LastReceiptCost** (decimal) - Koszt ostatniej dostawy
- **LastReceiptDate** (datetime) - Data ostatniej dostawy
- **MinOrderQty** (int) - Minimalna ilość
- **MaxOrderQty** (int) - Maksymalna ilość
- **OnOrderQty** (int) - W otwartych zamówieniach
- **UnitMeasureCode** (nchar, FK)
- **ModifiedDate** (datetime)

### PurchaseOrderHeader (Nagłówki zamówień zakupu)
- **PurchaseOrderID** (int, PK)
- **RevisionNumber** (tinyint) - Numer wersji dokumentu
- **Status** (tinyint) - Status (1=pending, 2=approved, 3=rejected, 4=done)
- **EmployeeID** (int, FK) - Pracownik zamawiający
- **VendorID** (int, FK) - Dostawca
- **ShipMethodID** (int, FK) - Sposób wysyłki
- **OrderDate** (datetime) - Data zamówienia
- **ShipDate** (datetime) - Data wysyłki
- **SubTotal** (money) - Subtotal
- **TaxAmt** (money) - Podatek
- **Freight** (money) - Wysyłka
- **TotalDue** (money) - Razem do zapłaty
- **ModifiedDate** (datetime)

### PurchaseOrderDetail (Szczegóły zamówień zakupu)
- **PurchaseOrderID** (int, PK, FK)
- **PurchaseOrderDetailID** (int, PK) - Indeks pozycji
- **DueDate** (datetime) - Data dostawy
- **OrderQty** (smallint) - Ilość zamówiona
- **ProductID** (int, FK)
- **UnitPrice** (money) - Cena jednostkowa
- **LineTotal** (money, computed) - Razem dla pozycji
- **ReceivedQty** (int) - Ilość otrzymana
- **RejectedQty** (int) - Ilość odrzucona
- **StockedQty** (int) - Ilość złożona na magazyn
- **ModifiedDate** (datetime)

---

## Typy Danych Niestandardowe

Baza definiuje własne typy danych:

- **Name** - nvarchar(50) - dla nazw
- **AccountNumber** - nvarchar(15) - dla numerów kont
- **Flag** - bit not null - dla flag binarnych
- **NameStyle** - bit not null - dla stylów nazw
- **OrderNumber** - nvarchar(25) - dla numerów zamówień
- **Phone** - nvarchar(25) - dla numerów telefonów

---

## Funkcje

### dbo.ufnLeadingZeros
Konwertuje liczbę na nvarchar z wiodącymi zerami (8 znaków)

---

## Schematy XML

Baza zawiera kilka schematów XML:

- **AdditionalContactInfoSchemaCollection** - Dodatkowe informacje kontaktowe
- **HRResumeSchemaCollection** - CV pracowników
- **IndividualSurveySchemaCollection** - Ankiety klientów
- **ManufacturingInstructionsSchemaCollection** - Instrukcje montażu
- **ProductDescriptionSchemaCollection** - Opisy produktów
- **StoreSurveySchemaCollection** - Ankiety sklepów

---

## Kluczowe Ograniczenia (Constraints)

- **Check constraints** - Walidacja danych (np. daty zatrudnienia, wysokość wynagrodzenia)
- **Foreign Key constraints** - Zachowanie integralności referencyjalnej
- **Unique constraints** - Unikalność wybranych kolumn
- **Default constraints** - Wartości domyślne (np. GETDATE())

---

## Indeksy

Tabele posiadają indeksy na:
- Klucze główne (Primary Keys) - clustered indexes
- Klucze obce (Foreign Keys)
- Kolumny zawarte w wyszukiwaniach i sortowaniach (SearchId, Name, itp.)
- Unikalne kolumny (Unique indexes)
