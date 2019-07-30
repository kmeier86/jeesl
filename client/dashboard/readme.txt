sudo apt-get update && sudo apt-get upgrade

sudo apt-get install -y unclutter xdotool

create project folder
------------------------
cd /home/pi
sudo mkdir jeesl-uhd

copy project files in project folder (jeesl-uhd.zip)

make kiosk script runable
------------------------
cd jeesl-uhd
sudo chmod +x kiosk

Edit autostart script
------------------------
sudo nano /etc/xdg/lxsession/LXDE-pi/autostart
add following line to autostart script
sh /home/pi/jeesl-uhd/kiosk.sh


