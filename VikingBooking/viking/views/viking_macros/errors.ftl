<#macro errors key>
	<#if (vikingErrors?? && vikingErrors[key]??)>
		<div class="errors">
			<p>${vikingErrors[key]}</p>
		</div>	
	</#if>
</#macro>

<#macro errorClass key>
	<#if (vikingErrors?? && vikingErrors[key]??)>error</#if>
</#macro>