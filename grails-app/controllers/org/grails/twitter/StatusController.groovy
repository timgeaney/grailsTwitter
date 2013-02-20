package org.grails.twitter

class StatusController {

	def springSecurityService

    def index() { 
    	def messages = currentUserTimeline()
    	[messages: messages]
    }

    def updateStatus = {
    	def status = new Status(message: params.message)
    	status.author = lookupPerson()
    	status.save()
    	def message = currentUserTimeline()
    	render template: 'messages', collection:messages, var: 'message'
    }

    private currentUserTimeline()
    {
    	def per = lookupPerson()
	    def messages = Status.withCriteria {
	    	author {
	    		eq 'username', per.username
	    	}
	    	
	    	maxResults 10
	    	order 'dateCreated', 'desc'
    	}
    	messages

    }

    private lookupPerson() 
    {
    	Person.get(springSecurityService.principal.id)
    }
}
