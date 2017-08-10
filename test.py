#!/usr/bin/python

from __future__ import print_function
try:
	import http.client as httplib
except:
	import httplib
import sys

# SERVER INFORMATION
PING_HOSTNAME = 'localhost'
PING_PORT = 8080

# SESSION COMMANDS
CREATE = 'create'
UPDATE = 'update'
GETALL = 'getall'
GET = 'get'
DELETE = 'delete'

def send_request(method, uri, body=None):
	"""Sends a request to the REST server"""
	headers = {}
	if body:
		headers['Content-Type'] = 'application/json; charset=utf-8'
	con = httplib.HTTPConnection(PING_HOSTNAME, PING_PORT)
	con.request(method, uri, body, headers)
	res = con.getresponse()
	return res.status, res.reason, res.read()

def get_all_employees():
	"""Returns all employees"""
	return send_request('GET', '/staff')

def get_employee(eid):
	"""Returns the employee matching the eid"""
	return send_request('GET', '/staff/' + eid)

def new_employee(body):
	"""Creates a new employee"""
	return send_request('POST', '/staff', body)

def delete_employee(eid):
	"""Deletes an employee"""
	return send_request('DELETE', '/staff/' + eid)

def update_employee(eid, body):
	"""Updates an employee"""
	return send_request('PUT', '/staff/' + eid, body)

if __name__ == '__main__':
	if len(sys.argv) < 2:
		print('USAGE:', sys.argv[0], '<command> <parameters...>')
		sys.exit(1)

	# Parse the command
	command = sys.argv[1]
	status, reason, res = None, None, None
	if (command == CREATE):
		status, reason, res = new_employee(sys.argv[2])
	elif (command == UPDATE):
		status, reason, res = update_employee(sys.argv[2], sys.argv[3])
	elif (command == GETALL):
		status, reason, res = get_all_employees()
	elif (command == GET):
		status, reason, res = get_employee(sys.argv[2])
	elif (command == DELETE):
		status, reason, res = delete_employee(sys.argv[2])
	else:
		print('Error: Unknown command', command)

	# Print the action results
	print(status, reason)
	if (res != ''):
		print(res)
