<#macro successNotification message="">
	<#if (message?length > 0)>
		<div class="portlet-msg-success">
			${message}
		</div>
	</#if>
</#macro>

<#macro failureNotification message="">
	<#if (message?length > 0)>
		<div class="portlet-msg-error">
			${message}
		</div>
	</#if>
</#macro>