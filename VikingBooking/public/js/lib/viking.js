function VK() {};

VK.pd = {};

VK.setPortletData = function(portletId, data) {
	VK.pd[portletId] = data;
};

VK.getPortletData = function(portletId) {
	return VK.pd[portletId];
};
