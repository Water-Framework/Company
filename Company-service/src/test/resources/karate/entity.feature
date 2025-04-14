# Generated with Water Generator
# The Goal of feature test is to ensure the correct format of json responses
# If you want to perform functional test please refer to ApiTest
Feature: Check Company Rest Api Response

  Scenario: Company CRUD Operations

    Given header Content-Type = 'application/json'
    And header Accept = 'application/json'
    Given url serviceBaseUrl+'/water/companies'
    # ---- Add entity fields here -----
    And request 
    """ {
      "businessName": 'businessName',
      "invoiceAddress": 'invoiceAddress',
      "city": 'city',
      "postalCode": 'postalCode',
      "nation": 'nation',
      "vatNumber": 'vatNumber'
    }
    """
    # ---------------------------------
    When method POST
    Then status 200
    # ---- Matching required response json ----
    And match response ==
    """
      { "id": #number,
        "entityVersion":1,
        "entityCreateDate":'#number',
        "entityModifyDate":'#number',
        "businessName": 'businessName',
        "invoiceAddress": 'invoiceAddress',
        "city": 'city',
        "postalCode": 'postalCode',
        "nation": 'nation',
        "vatNumber": 'vatNumber'
       }
    """
    * def entityId = response.id
    
    # --------------- UPDATE -----------------------------

    Given header Content-Type = 'application/json'
    And header Accept = 'application/json'
    Given url serviceBaseUrl+'/water/companies'
    # ---- Add entity fields here -----
    And request 
    """ { 
          "id":"#(entityId)",
          "entityVersion":1,
          "businessName": 'businessNameUpdated',
          "invoiceAddress": 'invoiceAddress',
          "city": 'city',
          "postalCode": 'postalCode',
          "nation": 'nation',
          "vatNumber": 'vatNumber'
    } 
    """
    # ---------------------------------
    When method PUT
    Then status 200
    # ---- Matching required response json ----
    And match response ==
    """
      { "id": #number,
        "entityVersion":2,
        "entityCreateDate":'#number',
        "entityModifyDate":'#number',
        "businessName": 'businessNameUpdated',
        "invoiceAddress": 'invoiceAddress',
        "city": 'city',
        "postalCode": 'postalCode',
        "nation": 'nation',
        "vatNumber": 'vatNumber'
       }
    """
  
  # --------------- FIND -----------------------------

    Given header Content-Type = 'application/json'
    And header Accept = 'application/json'
    Given url serviceBaseUrl+'/water/companies/'+entityId
    # ---------------------------------
    When method GET
    Then status 200
    # ---- Matching required response json ----
    And match response ==
    """
      { "id": #number,
        "entityVersion":2,
        "entityCreateDate":'#number',
        "entityModifyDate":'#number',
        "businessName": 'businessNameUpdated',
        "invoiceAddress": 'invoiceAddress',
        "city": 'city',
        "postalCode": 'postalCode',
        "nation": 'nation',
        "vatNumber": 'vatNumber'
       }
    """
    
  # --------------- FIND ALL -----------------------------

    Given header Content-Type = 'application/json'
    And header Accept = 'application/json'
    Given url serviceBaseUrl+'/water/companies'
    When method GET
    Then status 200
    And match response.results contains
    """

    {
    "id": #number,
    "entityVersion":2,
    "entityCreateDate":'#number',
    "entityModifyDate":'#number',
    "businessName": 'businessNameUpdated',
    "invoiceAddress": 'invoiceAddress',
    "city": 'city',
    "postalCode": 'postalCode',
    "nation": 'nation',
    "vatNumber": 'vatNumber'
    }
    """
  
  # --------------- DELETE -----------------------------

    Given header Content-Type = 'application/json'
    And header Accept = 'application/json'
    Given url serviceBaseUrl+'/water/companies/'+entityId
    When method DELETE
    # 204 because delete response is empty, so the status code is "no content" but is ok
    Then status 204
