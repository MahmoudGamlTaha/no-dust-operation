
function popupClosedListener(event) 
{ 
  var source = event.getSource(); 
  var popupId = source.getClientId(); 
  var params = {}; 
  params['popupId'] = popupId; 
  var type = "serverPopupClosed";
  var immediate = true;
  AdfCustomEvent.queue(source, type, params, immediate);
}
  