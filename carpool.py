# sudo pip3 install Flask-PyMongo
# sudo pip3 install pymongo[srv]
# sudo pip3 install flask-socketio
# sudo pip3 install eventlet


from flask import Flask, render_template,  request, escape, jsonify
from flask_socketio import SocketIO, emit
from flask_pymongo import PyMongo
from bson.json_util import dumps, loads 
from pymongo import MongoClient

app = Flask(__name__)

app.config["MONGO_URI"] = "mongodb+srv://carpool:Pass123@cluster0.9on7w.mongodb.net/project?retryWrites=true&w=majority"
 # replace the URI with your own connection
mongo = PyMongo(app)

#UserEntity
@app.route('/getalluser')
def get_all_user():
    email = mongo.db.UserEntity.find({},{"_id":0})
    
    emailList = list(email)
    return jsonify(emailList)  
    
@app.route('/getuser')    
def get_User():
   
    userData = request.args.get("email");
    email = mongo.db.UserEntity.find({"email":userData},{"_id":0})
    
    emailList = list(email)
    return jsonify(emailList)
    
#LocationEntity
@app.route('/getalllocation')
def get_all_location():
    location = mongo.db.LocationEntity.find({},{"_id":0})
    
    locationList = list(location)
    return jsonify(locationList)  
    
@app.route('/getlocation')    
def get_location():
   
    locationData = request.args.get("Landmark");
    locationList = mongo.db.LocationEntity.find({"Landmark":locationData},{"_id":0})
    
    locationList = list(locationList)
    return jsonify(locationList)
    
#RideDetails
@app.route('/getallridedetails')
def get_all_ride_details():
    ride = mongo.db.RideDetails.find({},{"_id":0})
    
    rideList = list(ride)
    return jsonify(rideList)  
    
@app.route('/getridedetails')    
def get_ride_details():
   
    rideData = request.args.get("userId");
    rideList = mongo.db.RideDetails.find({"userId":rideData},{"_id":0})
    
    rideList = list(rideList)
    return jsonify(rideList)
    
    
#ConfirmedRequest    
@app.route('/getallrequest')
def get_all_request():
    request = mongo.db.ConfirmedRequest.find({},{"_id":0})
    
    requestList = list(request)
    return jsonify(requestList)  
    
@app.route('/getrequest')    
def get_request():
   
    requestData = int(request.args.get("rideId"));
    requestList = mongo.db.ConfirmedRequest.find({"rideId":requestData},{"_id":0})
    
    requestList = list(requestList)
    return jsonify(requestList)
    
@app.route('/adduser', methods=['POST']) #endpoint
def add_user():
    
    email = request.args.get("email")
    licenseNumber = request.args.get("licenseNumber")
    phoneNumber =  request.args.get("phoneNumber")
    user = {'email' : email,'licenseNumber':licenseNumber, 'phoneNumber':phoneNumber} #json to insert
    mongo.db.UserEntity.insert_one(user) 
    user = mongo.db.UserEntity.find({},{"_id":0}) 
    listOfUsers = list(user)
    return jsonify(listOfUsers)
    
@app.route('/addride',methods=['POST']) #endpoint http://35.153.194.142:5000/addride?SourceLocation=Canada%20Place&DestinationLocation=YVR%20Airport&Time=10am&Date=13/02/2021&seatsCount=2&isOfferingRide=true&isAskingRide=false&userId=saira24@gmail%2Ecom&rideId=4
def add_ride():
    
    sourceLocation = request.args.get("SourceLocation")
    destinationLocation = request.args.get("DestinationLocation")
    time = request.args.get("Time")
    date = request.args.get("Date")
    seatsCount =  request.args.get("seatsCount")
    isOfferingRide = request.args.get("isOfferingRide")
    isAskingRide  =  request.args.get("isAskingRide")
    userId = request.args.get("userId")
    rideId = request.args.get("rideId")
    
    ride = {'SourceLocation' : sourceLocation, 'DestinationLocation':destinationLocation, 'Time':time , 'Date': date,  'seatsCount':seatsCount, 'isOfferingRide':isOfferingRide, 'isAskingRide':isAskingRide, 'userId':userId,'rideId':rideId}
    mongo.db.RideDetails.insert_one(ride) 
    rides = mongo.db.RideDetails.find({},{"_id":0}) 
    listOfRides = list(rides)
    return jsonify(listOfRides)

@app.route('/confirmRide', methods=['POST']) #endpoint
def confirm_ride():
    
    driverId = request.args.get("driverId")
    userId =  request.args.get("userId")
    rideId = request.args.get("rideId")
    ride = {'driverId' : driverId, 'userId':userId, 'rideId':rideId} #json to insert
    #user = {'email' : email}
    mongo.db.ConfirmedRequest.insert_one(ride) 
    rideDetails = mongo.db.ConfirmedRequest.find({},{"_id":0}) 
    listOfRides = list(rideDetails)
    return jsonify(listOfRides)
    
    
    
    
if __name__ == '__main__':
    app.run(host='0.0.0.0',debug=True)

